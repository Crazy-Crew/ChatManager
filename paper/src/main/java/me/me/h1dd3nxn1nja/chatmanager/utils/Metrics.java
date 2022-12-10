package me.me.h1dd3nxn1nja.chatmanager.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;

/**
 * bStats collects some data for plugin authors.
 *
 * Check out https://bStats.org/ to learn more about bStats!
 */
public class Metrics {

	private final Plugin plugin;

	private final MetricsBase metricsBase;

	/**
	 * Creates a new Metrics instance.
	 *
	 * @param plugin    Your plugin instance.
	 * @param serviceId The id of the service. It can be found at
	 *                  <a href="https://bstats.org/what-is-my-plugin-id">What is my
	 *                  plugin id?</a>
	 */
	public Metrics(JavaPlugin plugin, int serviceId) {
		this.plugin = plugin;
		// Get the config file
		File bStatsFolder = new File(plugin.getDataFolder().getParentFile(), "bStats");
		File configFile = new File(bStatsFolder, "config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		if (!config.isSet("serverUuid")) {
			config.addDefault("enabled", true);
			config.addDefault("serverUuid", UUID.randomUUID().toString());
			config.addDefault("logFailedRequests", false);
			config.addDefault("logSentData", false);
			config.addDefault("logResponseStatusText", false);
			// Inform the server owners about bStats
			config.options()
					.header("bStats (https://bStats.org) collects some basic information for plugin authors, like how\n"
							+ "many people use their plugin and their total player count. It's recommended to keep bStats\n"
							+ "enabled, but if you're not comfortable with this, you can turn this setting off. There is no\n"
							+ "performance penalty associated with having metrics enabled, and data sent to bStats is fully\n"
							+ "anonymous.")
					.copyDefaults(true);
			try {
				config.save(configFile);
			} catch (IOException ignored) {
			}
		}
		// Load the data
		boolean enabled = config.getBoolean("enabled", true);
		String serverUUID = config.getString("serverUuid");
		boolean logErrors = config.getBoolean("logFailedRequests", false);
		boolean logSentData = config.getBoolean("logSentData", false);
		boolean logResponseStatusText = config.getBoolean("logResponseStatusText", false);
		metricsBase = new MetricsBase("bukkit", serverUUID, serviceId, enabled, this::appendPlatformData,
				this::appendServiceData, submitDataTask -> Bukkit.getScheduler().runTask(plugin, submitDataTask),
				plugin::isEnabled, (message, error) -> this.plugin.getLogger().log(Level.WARNING, message, error),
				(message) -> this.plugin.getLogger().log(Level.INFO, message), logErrors, logSentData,
				logResponseStatusText);
	}

	/**
	 * Adds a custom chart.
	 *
	 * @param chart The chart to add.
	 */
	public void addCustomChart(CustomChart chart) {
		metricsBase.addCustomChart(chart);
	}

	private void appendPlatformData(JsonObjectBuilder builder) {
		builder.appendField("playerAmount", getPlayerAmount());
		builder.appendField("onlineMode", Bukkit.getOnlineMode() ? 1 : 0);
		builder.appendField("bukkitVersion", Bukkit.getVersion());
		builder.appendField("bukkitName", Bukkit.getName());
		builder.appendField("javaVersion", System.getProperty("java.version"));
		builder.appendField("osName", System.getProperty("os.name"));
		builder.appendField("osArch", System.getProperty("os.arch"));
		builder.appendField("osVersion", System.getProperty("os.version"));
		builder.appendField("coreCount", Runtime.getRuntime().availableProcessors());
	}

	private void appendServiceData(JsonObjectBuilder builder) {
		builder.appendField("pluginVersion", plugin.getDescription().getVersion());
	}

	private int getPlayerAmount() {
		try {
			// Around MC 1.8 the return type was changed from an array to a collection,
			// This fixes java.lang.NoSuchMethodError:
			// org.bukkit.Bukkit.getOnlinePlayers()Ljava/util/Collection;
			Method onlinePlayersMethod = Class.forName("org.bukkit.Server").getMethod("getOnlinePlayers");
			return onlinePlayersMethod.getReturnType().equals(Collection.class)
					? ((Collection<?>) onlinePlayersMethod.invoke(Bukkit.getServer())).size()
					: ((Player[]) onlinePlayersMethod.invoke(Bukkit.getServer())).length;
		} catch (Exception e) {
			// Just use the new method if the reflection failed
			return Bukkit.getOnlinePlayers().size();
		}
	}

	public static class MetricsBase {

		/** The version of the Metrics class. */
		public static final String METRICS_VERSION = "2.2.1";

		private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1,
				task -> new Thread(task, "bStats-Metrics"));

		private static final String REPORT_URL = "https://bStats.org/api/v2/data/%s";

		private final String platform;

		private final String serverUuid;

		private final int serviceId;

		private final Consumer<JsonObjectBuilder> appendPlatformDataConsumer;

		private final Consumer<JsonObjectBuilder> appendServiceDataConsumer;

		private final Consumer<Runnable> submitTaskConsumer;

		private final Supplier<Boolean> checkServiceEnabledSupplier;

		private final BiConsumer<String, Throwable> errorLogger;

		private final Consumer<String> infoLogger;

		private final boolean logErrors;

		private final boolean logSentData;

		private final boolean logResponseStatusText;

		private final Set<CustomChart> customCharts = new HashSet<>();

		private final boolean enabled;

		/**
		 * Creates a new MetricsBase class instance.
		 *
		 * @param platform                    The platform of the service.
		 * @param serviceId                   The id of the service.
		 * @param serverUuid                  The server uuid.
		 * @param enabled                     Whether or not data sending is enabled.
		 * @param appendPlatformDataConsumer  A consumer that receives a
		 *                                    {@code JsonObjectBuilder} and appends all
		 *                                    platform-specific data.
		 * @param appendServiceDataConsumer   A consumer that receives a
		 *                                    {@code JsonObjectBuilder} and appends all
		 *                                    service-specific data.
		 * @param submitTaskConsumer          A consumer that takes a runnable with the
		 *                                    submit task. This can be used to delegate
		 *                                    the data collection to a another thread to
		 *                                    prevent errors caused by concurrency. Can
		 *                                    be {@code null}.
		 * @param checkServiceEnabledSupplier A supplier to check if the service is
		 *                                    still enabled.
		 * @param errorLogger                 A consumer that accepts log message and an
		 *                                    error.
		 * @param infoLogger                  A consumer that accepts info log messages.
		 * @param logErrors                   Whether or not errors should be logged.
		 * @param logSentData                 Whether or not the sent data should be
		 *                                    logged.
		 * @param logResponseStatusText       Whether or not the response status text
		 *                                    should be logged.
		 */
		public MetricsBase(String platform, String serverUuid, int serviceId, boolean enabled,
				Consumer<JsonObjectBuilder> appendPlatformDataConsumer,
				Consumer<JsonObjectBuilder> appendServiceDataConsumer, Consumer<Runnable> submitTaskConsumer,
				Supplier<Boolean> checkServiceEnabledSupplier, BiConsumer<String, Throwable> errorLogger,
				Consumer<String> infoLogger, boolean logErrors, boolean logSentData, boolean logResponseStatusText) {
			this.platform = platform;
			this.serverUuid = serverUuid;
			this.serviceId = serviceId;
			this.enabled = enabled;
			this.appendPlatformDataConsumer = appendPlatformDataConsumer;
			this.appendServiceDataConsumer = appendServiceDataConsumer;
			this.submitTaskConsumer = submitTaskConsumer;
			this.checkServiceEnabledSupplier = checkServiceEnabledSupplier;
			this.errorLogger = errorLogger;
			this.infoLogger = infoLogger;
			this.logErrors = logErrors;
			this.logSentData = logSentData;
			this.logResponseStatusText = logResponseStatusText;
			checkRelocation();
			if (enabled) {
				startSubmitting();
			}
		}

		public void addCustomChart(CustomChart chart) {
			this.customCharts.add(chart);
		}

		private void startSubmitting() {
			final Runnable submitTask = () -> {
				if (!enabled || !checkServiceEnabledSupplier.get()) {
					// Submitting data or service is disabled
					scheduler.shutdown();
					return;
				}
				if (submitTaskConsumer != null) {
					submitTaskConsumer.accept(this::submitData);
				} else {
					this.submitData();
				}
			};
			// Many servers tend to restart at a fixed time at xx:00 which causes an uneven
			// distribution
			// of requests on the
			// bStats backend. To circumvent this problem, we introduce some randomness into
			// the initial
			// and second delay.
			// WARNING: You must not modify and part of this Metrics class, including the
			// submit delay or
			// frequency!
			// WARNING: Modifying this code will get your plugin banned on bStats. Just
			// don't do it!
			long initialDelay = (long) (1000 * 60 * (3 + Math.random() * 3));
			long secondDelay = (long) (1000 * 60 * (Math.random() * 30));
			scheduler.schedule(submitTask, initialDelay, TimeUnit.MILLISECONDS);
			scheduler.scheduleAtFixedRate(submitTask, initialDelay + secondDelay, 1000 * 60 * 30,
					TimeUnit.MILLISECONDS);
		}

		private void submitData() {
			final JsonObjectBuilder baseJsonBuilder = new JsonObjectBuilder();
			appendPlatformDataConsumer.accept(baseJsonBuilder);
			final JsonObjectBuilder serviceJsonBuilder = new JsonObjectBuilder();
			appendServiceDataConsumer.accept(serviceJsonBuilder);
			JsonObjectBuilder.JsonObject[] chartData = customCharts.stream()
					.map(customChart -> customChart.getRequestJsonObject(errorLogger, logErrors))
					.filter(Objects::nonNull).toArray(JsonObjectBuilder.JsonObject[]::new);
			serviceJsonBuilder.appendField("id", serviceId);
			serviceJsonBuilder.appendField("customCharts", chartData);
			baseJsonBuilder.appendField("service", serviceJsonBuilder.build());
			baseJsonBuilder.appendField("serverUUID", serverUuid);
			baseJsonBuilder.appendField("metricsVersion", METRICS_VERSION);
			JsonObjectBuilder.JsonObject data = baseJsonBuilder.build();
			scheduler.execute(() -> {
				try {
					// Send the data
					sendData(data);
				} catch (Exception e) {
					// Something went wrong! :(
					if (logErrors) {
						errorLogger.accept("Could not submit bStats metrics data", e);
					}
				}
			});
		}

		private void sendData(JsonObjectBuilder.JsonObject data) throws Exception {
			if (logSentData) {
				infoLogger.accept("Sent bStats metrics data: " + data.toString());
			}
			String url = String.format(REPORT_URL, platform);
			HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
			// Compress the data to save bandwidth
			byte[] compressedData = compress(data.toString());
			connection.setRequestMethod("POST");
			connection.addRequestProperty("Accept", "application/json");
			connection.addRequestProperty("Connection", "close");
			connection.addRequestProperty("Content-Encoding", "gzip");
			connection.addRequestProperty("Content-Length", String.valueOf(compressedData.length));
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("User-Agent", "Metrics-Service/1");
			connection.setDoOutput(true);
			try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
				outputStream.write(compressedData);
			}
			StringBuilder builder = new StringBuilder();
			try (BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(connection.getInputStream()))) {
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					builder.append(line);
				}
			}
			if (logResponseStatusText) {
				infoLogger.accept("Sent data to bStats and received response: " + builder);
			}
		}

		/** Checks that the class was properly relocated. */
		private void checkRelocation() {
			// You can use the property to disable the check in your test environment
			if (System.getProperty("bstats.relocatecheck") == null
					|| !System.getProperty("bstats.relocatecheck").equals("false")) {
				// Maven's Relocate is clever and changes strings, too. So we have to use this
				// little
				// "trick" ... :D
				final String defaultPackage = new String(
						new byte[] { 'o', 'r', 'g', '.', 'b', 's', 't', 'a', 't', 's' });
				final String examplePackage = new String(
						new byte[] { 'y', 'o', 'u', 'r', '.', 'p', 'a', 'c', 'k', 'a', 'g', 'e' });
				// We want to make sure no one just copy & pastes the example and uses the wrong
				// package
				// names
				if (MetricsBase.class.getPackage().getName().startsWith(defaultPackage)
						|| MetricsBase.class.getPackage().getName().startsWith(examplePackage)) {
					throw new IllegalStateException("bStats Metrics class has not been relocated correctly!");
				}
			}
		}

		/**
		 * Gzips the given string.
		 *
		 * @param str The string to gzip.
		 * @return The gzipped string.
		 */
		private static byte[] compress(final String str) throws IOException {
			if (str == null) {
				return null;
			}
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			try (GZIPOutputStream gzip = new GZIPOutputStream(outputStream)) {
				gzip.write(str.getBytes(StandardCharsets.UTF_8));
			}
			return outputStream.toByteArray();
		}
	}

	public static class AdvancedBarChart extends CustomChart {

		private final Callable<Map<String, int[]>> callable;

		/**
		 * Class constructor.
		 *
		 * @param chartId  The id of the chart.
		 * @param callable The callable which is used to request the chart data.
		 */
		public AdvancedBarChart(String chartId, Callable<Map<String, int[]>> callable) {
			super(chartId);
			this.callable = callable;
		}

		@Override
		protected JsonObjectBuilder.JsonObject getChartData() throws Exception {
			JsonObjectBuilder valuesBuilder = new JsonObjectBuilder();
			Map<String, int[]> map = callable.call();
			if (map == null || map.isEmpty()) {
				// Null = skip the chart
				return null;
			}
			boolean allSkipped = true;
			for (Map.Entry<String, int[]> entry : map.entrySet()) {
				if (entry.getValue().length == 0) {
					// Skip this invalid
					continue;
				}
				allSkipped = false;
				valuesBuilder.appendField(entry.getKey(), entry.getValue());
			}
			if (allSkipped) {
				// Null = skip the chart
				return null;
			}
			return new JsonObjectBuilder().appendField("values", valuesBuilder.build()).build();
		}
	}

	public static class SimpleBarChart extends CustomChart {

		private final Callable<Map<String, Integer>> callable;

		/**
		 * Class constructor.
		 *
		 * @param chartId  The id of the chart.
		 * @param callable The callable which is used to request the chart data.
		 */
		public SimpleBarChart(String chartId, Callable<Map<String, Integer>> callable) {
			super(chartId);
			this.callable = callable;
		}

		@Override
		protected JsonObjectBuilder.JsonObject getChartData() throws Exception {
			JsonObjectBuilder valuesBuilder = new JsonObjectBuilder();
			Map<String, Integer> map = callable.call();
			if (map == null || map.isEmpty()) {
				// Null = skip the chart
				return null;
			}
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				valuesBuilder.appendField(entry.getKey(), new int[] { entry.getValue() });
			}
			return new JsonObjectBuilder().appendField("values", valuesBuilder.build()).build();
		}
	}

	public static class MultiLineChart extends CustomChart {

		private final Callable<Map<String, Integer>> callable;

		/**
		 * Class constructor.
		 *
		 * @param chartId  The id of the chart.
		 * @param callable The callable which is used to request the chart data.
		 */
		public MultiLineChart(String chartId, Callable<Map<String, Integer>> callable) {
			super(chartId);
			this.callable = callable;
		}

		@Override
		protected JsonObjectBuilder.JsonObject getChartData() throws Exception {
			JsonObjectBuilder valuesBuilder = new JsonObjectBuilder();
			Map<String, Integer> map = callable.call();
			if (map == null || map.isEmpty()) {
				// Null = skip the chart
				return null;
			}
			boolean allSkipped = true;
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				if (entry.getValue() == 0) {
					// Skip this invalid
					continue;
				}
				allSkipped = false;
				valuesBuilder.appendField(entry.getKey(), entry.getValue());
			}
			if (allSkipped) {
				// Null = skip the chart
				return null;
			}
			return new JsonObjectBuilder().appendField("values", valuesBuilder.build()).build();
		}
	}

	public static class AdvancedPie extends CustomChart {

		private final Callable<Map<String, Integer>> callable;

		/**
		 * Class constructor.
		 *
		 * @param chartId  The id of the chart.
		 * @param callable The callable which is used to request the chart data.
		 */
		public AdvancedPie(String chartId, Callable<Map<String, Integer>> callable) {
			super(chartId);
			this.callable = callable;
		}

		@Override
		protected JsonObjectBuilder.JsonObject getChartData() throws Exception {
			JsonObjectBuilder valuesBuilder = new JsonObjectBuilder();
			Map<String, Integer> map = callable.call();
			if (map == null || map.isEmpty()) {
				// Null = skip the chart
				return null;
			}
			boolean allSkipped = true;
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				if (entry.getValue() == 0) {
					// Skip this invalid
					continue;
				}
				allSkipped = false;
				valuesBuilder.appendField(entry.getKey(), entry.getValue());
			}
			if (allSkipped) {
				// Null = skip the chart
				return null;
			}
			return new JsonObjectBuilder().appendField("values", valuesBuilder.build()).build();
		}
	}

	public abstract static class CustomChart {

		private final String chartId;

		protected CustomChart(String chartId) {
			if (chartId == null) {
				throw new IllegalArgumentException("chartId must not be null");
			}
			this.chartId = chartId;
		}

		public JsonObjectBuilder.JsonObject getRequestJsonObject(BiConsumer<String, Throwable> errorLogger,
				boolean logErrors) {
			JsonObjectBuilder builder = new JsonObjectBuilder();
			builder.appendField("chartId", chartId);
			try {
				JsonObjectBuilder.JsonObject data = getChartData();
				if (data == null) {
					// If the data is null we don't send the chart.
					return null;
				}
				builder.appendField("data", data);
			} catch (Throwable t) {
				if (logErrors) {
					errorLogger.accept("Failed to get data for custom chart with id " + chartId, t);
				}
				return null;
			}
			return builder.build();
		}

		protected abstract JsonObjectBuilder.JsonObject getChartData() throws Exception;
	}

	public static class SingleLineChart extends CustomChart {

		private final Callable<Integer> callable;

		/**
		 * Class constructor.
		 *
		 * @param chartId  The id of the chart.
		 * @param callable The callable which is used to request the chart data.
		 */
		public SingleLineChart(String chartId, Callable<Integer> callable) {
			super(chartId);
			this.callable = callable;
		}

		@Override
		protected JsonObjectBuilder.JsonObject getChartData() throws Exception {
			int value = callable.call();
			if (value == 0) {
				// Null = skip the chart
				return null;
			}
			return new JsonObjectBuilder().appendField("value", value).build();
		}
	}

	public static class SimplePie extends CustomChart {

		private final Callable<String> callable;

		/**
		 * Class constructor.
		 *
		 * @param chartId  The id of the chart.
		 * @param callable The callable which is used to request the chart data.
		 */
		public SimplePie(String chartId, Callable<String> callable) {
			super(chartId);
			this.callable = callable;
		}

		@Override
		protected JsonObjectBuilder.JsonObject getChartData() throws Exception {
			String value = callable.call();
			if (value == null || value.isEmpty()) {
				// Null = skip the chart
				return null;
			}
			return new JsonObjectBuilder().appendField("value", value).build();
		}
	}

	public static class DrilldownPie extends CustomChart {

		private final Callable<Map<String, Map<String, Integer>>> callable;

		/**
		 * Class constructor.
		 *
		 * @param chartId  The id of the chart.
		 * @param callable The callable which is used to request the chart data.
		 */
		public DrilldownPie(String chartId, Callable<Map<String, Map<String, Integer>>> callable) {
			super(chartId);
			this.callable = callable;
		}

		@Override
		public JsonObjectBuilder.JsonObject getChartData() throws Exception {
			JsonObjectBuilder valuesBuilder = new JsonObjectBuilder();
			Map<String, Map<String, Integer>> map = callable.call();
			if (map == null || map.isEmpty()) {
				// Null = skip the chart
				return null;
			}
			boolean reallyAllSkipped = true;
			for (Map.Entry<String, Map<String, Integer>> entryValues : map.entrySet()) {
				JsonObjectBuilder valueBuilder = new JsonObjectBuilder();
				boolean allSkipped = true;
				for (Map.Entry<String, Integer> valueEntry : map.get(entryValues.getKey()).entrySet()) {
					valueBuilder.appendField(valueEntry.getKey(), valueEntry.getValue());
					allSkipped = false;
				}
				if (!allSkipped) {
					reallyAllSkipped = false;
					valuesBuilder.appendField(entryValues.getKey(), valueBuilder.build());
				}
			}
			if (reallyAllSkipped) {
				// Null = skip the chart
				return null;
			}
			return new JsonObjectBuilder().appendField("values", valuesBuilder.build()).build();
		}
	}

	/**
	 * An extremely simple JSON builder.
	 *
	 * <p>
	 * While this class is neither feature-rich nor the most performant one, it's
	 * sufficient enough for its use-case.
	 */
	public static class JsonObjectBuilder {

		private StringBuilder builder = new StringBuilder();

		private boolean hasAtLeastOneField = false;

		public JsonObjectBuilder() {
			builder.append("{");
		}

		/**
		 * Appends a null field to the JSON.
		 *
		 * @param key The key of the field.
		 * @return A reference to this object.
		 */
		public JsonObjectBuilder appendNull(String key) {
			appendFieldUnescaped(key, "null");
			return this;
		}

		/**
		 * Appends a string field to the JSON.
		 *
		 * @param key   The key of the field.
		 * @param value The value of the field.
		 * @return A reference to this object.
		 */
		public JsonObjectBuilder appendField(String key, String value) {
			if (value == null) {
				throw new IllegalArgumentException("JSON value must not be null");
			}
			appendFieldUnescaped(key, "\"" + escape(value) + "\"");
			return this;
		}

		/**
		 * Appends an integer field to the JSON.
		 *
		 * @param key   The key of the field.
		 * @param value The value of the field.
		 * @return A reference to this object.
		 */
		public JsonObjectBuilder appendField(String key, int value) {
			appendFieldUnescaped(key, String.valueOf(value));
			return this;
		}

		/**
		 * Appends an object to the JSON.
		 *
		 * @param key    The key of the field.
		 * @param object The object.
		 * @return A reference to this object.
		 */
		public JsonObjectBuilder appendField(String key, JsonObject object) {
			if (object == null) {
				throw new IllegalArgumentException("JSON object must not be null");
			}
			appendFieldUnescaped(key, object.toString());
			return this;
		}

		/**
		 * Appends a string array to the JSON.
		 *
		 * @param key    The key of the field.
		 * @param values The string array.
		 * @return A reference to this object.
		 */
		public JsonObjectBuilder appendField(String key, String[] values) {
			if (values == null) {
				throw new IllegalArgumentException("JSON values must not be null");
			}
			String escapedValues = Arrays.stream(values).map(value -> "\"" + escape(value) + "\"")
					.collect(Collectors.joining(","));
			appendFieldUnescaped(key, "[" + escapedValues + "]");
			return this;
		}

		/**
		 * Appends an integer array to the JSON.
		 *
		 * @param key    The key of the field.
		 * @param values The integer array.
		 * @return A reference to this object.
		 */
		public JsonObjectBuilder appendField(String key, int[] values) {
			if (values == null) {
				throw new IllegalArgumentException("JSON values must not be null");
			}
			String escapedValues = Arrays.stream(values).mapToObj(String::valueOf).collect(Collectors.joining(","));
			appendFieldUnescaped(key, "[" + escapedValues + "]");
			return this;
		}

		/**
		 * Appends an object array to the JSON.
		 *
		 * @param key    The key of the field.
		 * @param values The integer array.
		 * @return A reference to this object.
		 */
		public JsonObjectBuilder appendField(String key, JsonObject[] values) {
			if (values == null) {
				throw new IllegalArgumentException("JSON values must not be null");
			}
			String escapedValues = Arrays.stream(values).map(JsonObject::toString).collect(Collectors.joining(","));
			appendFieldUnescaped(key, "[" + escapedValues + "]");
			return this;
		}

		/**
		 * Appends a field to the object.
		 *
		 * @param key          The key of the field.
		 * @param escapedValue The escaped value of the field.
		 */
		private void appendFieldUnescaped(String key, String escapedValue) {
			if (builder == null) {
				throw new IllegalStateException("JSON has already been built");
			}
			if (key == null) {
				throw new IllegalArgumentException("JSON key must not be null");
			}
			if (hasAtLeastOneField) {
				builder.append(",");
			}
			builder.append("\"").append(escape(key)).append("\":").append(escapedValue);
			hasAtLeastOneField = true;
		}

		/**
		 * Builds the JSON string and invalidates this builder.
		 *
		 * @return The built JSON string.
		 */
		public JsonObject build() {
			if (builder == null) {
				throw new IllegalStateException("JSON has already been built");
			}
			JsonObject object = new JsonObject(builder.append("}").toString());
			builder = null;
			return object;
		}

		/**
		 * Escapes the given string like stated in https://www.ietf.org/rfc/rfc4627.txt.
		 *
		 * <p>
		 * This method escapes only the necessary characters '"', '\'. and '\u0000' -
		 * '\u001F'. Compact escapes are not used (e.g., '\n' is escaped as "\u000a" and
		 * not as "\n").
		 *
		 * @param value The value to escape.
		 * @return The escaped value.
		 */
		private static String escape(String value) {
			final StringBuilder builder = new StringBuilder();
			for (int i = 0; i < value.length(); i++) {
				char c = value.charAt(i);
				if (c == '"') {
					builder.append("\\\"");
				} else if (c == '\\') {
					builder.append("\\\\");
				} else if (c <= '\u000F') {
					builder.append("\\u000").append(Integer.toHexString(c));
				} else if (c <= '\u001F') {
					builder.append("\\u00").append(Integer.toHexString(c));
				} else {
					builder.append(c);
				}
			}
			return builder.toString();
		}

		/**
		 * A super simple representation of a JSON object.
		 *
		 * <p>
		 * This class only exists to make methods of the {@link JsonObjectBuilder}
		 * type-safe and not allow a raw string inputs for methods like
		 * {@link JsonObjectBuilder#appendField(String, JsonObject)}.
		 */
		public static class JsonObject {

			private final String value;

			private JsonObject(String value) {
				this.value = value;
			}

			@Override
			public String toString() {
				return value;
			}
		}

		/**
		 * A enum which is used for custom maps.
		 */
		public enum Country {

			/**
			 * bStats will use the country of the server.
			 */
			AUTO_DETECT("AUTO", "Auto Detected"),

			ANDORRA("AD", "Andorra"), UNITED_ARAB_EMIRATES("AE", "United Arab Emirates"),
			AFGHANISTAN("AF", "Afghanistan"), ANTIGUA_AND_BARBUDA("AG", "Antigua and Barbuda"),
			ANGUILLA("AI", "Anguilla"), ALBANIA("AL", "Albania"), ARMENIA("AM", "Armenia"),
			NETHERLANDS_ANTILLES("AN", "Netherlands Antilles"), ANGOLA("AO", "Angola"), ANTARCTICA("AQ", "Antarctica"),
			ARGENTINA("AR", "Argentina"), AMERICAN_SAMOA("AS", "American Samoa"), AUSTRIA("AT", "Austria"),
			AUSTRALIA("AU", "Australia"), ARUBA("AW", "Aruba"), ALAND_ISLANDS("AX", "Åland Islands"),
			AZERBAIJAN("AZ", "Azerbaijan"), BOSNIA_AND_HERZEGOVINA("BA", "Bosnia and Herzegovina"),
			BARBADOS("BB", "Barbados"), BANGLADESH("BD", "Bangladesh"), BELGIUM("BE", "Belgium"),
			BURKINA_FASO("BF", "Burkina Faso"), BULGARIA("BG", "Bulgaria"), BAHRAIN("BH", "Bahrain"),
			BURUNDI("BI", "Burundi"), BENIN("BJ", "Benin"), SAINT_BARTHELEMY("BL", "Saint Barthélemy"),
			BERMUDA("BM", "Bermuda"), BRUNEI("BN", "Brunei"), BOLIVIA("BO", "Bolivia"),
			BONAIRE_SINT_EUSTATIUS_AND_SABA("BQ", "Bonaire, Sint Eustatius and Saba"), BRAZIL("BR", "Brazil"),
			BAHAMAS("BS", "Bahamas"), BHUTAN("BT", "Bhutan"), BOUVET_ISLAND("BV", "Bouvet Island"),
			BOTSWANA("BW", "Botswana"), BELARUS("BY", "Belarus"), BELIZE("BZ", "Belize"), CANADA("CA", "Canada"),
			COCOS_ISLANDS("CC", "Cocos Islands"),
			THE_DEMOCRATIC_REPUBLIC_OF_CONGO("CD", "The Democratic Republic Of Congo"),
			CENTRAL_AFRICAN_REPUBLIC("CF", "Central African Republic"), CONGO("CG", "Congo"),
			SWITZERLAND("CH", "Switzerland"), COTE_D_IVOIRE("CI", "Côte d'Ivoire"), COOK_ISLANDS("CK", "Cook Islands"),
			CHILE("CL", "Chile"), CAMEROON("CM", "Cameroon"), CHINA("CN", "China"), COLOMBIA("CO", "Colombia"),
			COSTA_RICA("CR", "Costa Rica"), CUBA("CU", "Cuba"), CAPE_VERDE("CV", "Cape Verde"),
			CURACAO("CW", "Curaçao"), CHRISTMAS_ISLAND("CX", "Christmas Island"), CYPRUS("CY", "Cyprus"),
			CZECH_REPUBLIC("CZ", "Czech Republic"), GERMANY("DE", "Germany"), DJIBOUTI("DJ", "Djibouti"),
			DENMARK("DK", "Denmark"), DOMINICA("DM", "Dominica"), DOMINICAN_REPUBLIC("DO", "Dominican Republic"),
			ALGERIA("DZ", "Algeria"), ECUADOR("EC", "Ecuador"), ESTONIA("EE", "Estonia"), EGYPT("EG", "Egypt"),
			WESTERN_SAHARA("EH", "Western Sahara"), ERITREA("ER", "Eritrea"), SPAIN("ES", "Spain"),
			ETHIOPIA("ET", "Ethiopia"), FINLAND("FI", "Finland"), FIJI("FJ", "Fiji"),
			FALKLAND_ISLANDS("FK", "Falkland Islands"), MICRONESIA("FM", "Micronesia"),
			FAROE_ISLANDS("FO", "Faroe Islands"), FRANCE("FR", "France"), GABON("GA", "Gabon"),
			UNITED_KINGDOM("GB", "United Kingdom"), GRENADA("GD", "Grenada"), GEORGIA("GE", "Georgia"),
			FRENCH_GUIANA("GF", "French Guiana"), GUERNSEY("GG", "Guernsey"), GHANA("GH", "Ghana"),
			GIBRALTAR("GI", "Gibraltar"), GREENLAND("GL", "Greenland"), GAMBIA("GM", "Gambia"), GUINEA("GN", "Guinea"),
			GUADELOUPE("GP", "Guadeloupe"), EQUATORIAL_GUINEA("GQ", "Equatorial Guinea"), GREECE("GR", "Greece"),
			SOUTH_GEORGIA_AND_THE_SOUTH_SANDWICH_ISLANDS("GS", "South Georgia And The South Sandwich Islands"),
			GUATEMALA("GT", "Guatemala"), GUAM("GU", "Guam"), GUINEA_BISSAU("GW", "Guinea-Bissau"),
			GUYANA("GY", "Guyana"), HONG_KONG("HK", "Hong Kong"),
			HEARD_ISLAND_AND_MCDONALD_ISLANDS("HM", "Heard Island And McDonald Islands"), HONDURAS("HN", "Honduras"),
			CROATIA("HR", "Croatia"), HAITI("HT", "Haiti"), HUNGARY("HU", "Hungary"), INDONESIA("ID", "Indonesia"),
			IRELAND("IE", "Ireland"), ISRAEL("IL", "Israel"), ISLE_OF_MAN("IM", "Isle Of Man"), INDIA("IN", "India"),
			BRITISH_INDIAN_OCEAN_TERRITORY("IO", "British Indian Ocean Territory"), IRAQ("IQ", "Iraq"),
			IRAN("IR", "Iran"), ICELAND("IS", "Iceland"), ITALY("IT", "Italy"), JERSEY("JE", "Jersey"),
			JAMAICA("JM", "Jamaica"), JORDAN("JO", "Jordan"), JAPAN("JP", "Japan"), KENYA("KE", "Kenya"),
			KYRGYZSTAN("KG", "Kyrgyzstan"), CAMBODIA("KH", "Cambodia"), KIRIBATI("KI", "Kiribati"),
			COMOROS("KM", "Comoros"), SAINT_KITTS_AND_NEVIS("KN", "Saint Kitts And Nevis"),
			NORTH_KOREA("KP", "North Korea"), SOUTH_KOREA("KR", "South Korea"), KUWAIT("KW", "Kuwait"),
			CAYMAN_ISLANDS("KY", "Cayman Islands"), KAZAKHSTAN("KZ", "Kazakhstan"), LAOS("LA", "Laos"),
			LEBANON("LB", "Lebanon"), SAINT_LUCIA("LC", "Saint Lucia"), LIECHTENSTEIN("LI", "Liechtenstein"),
			SRI_LANKA("LK", "Sri Lanka"), LIBERIA("LR", "Liberia"), LESOTHO("LS", "Lesotho"),
			LITHUANIA("LT", "Lithuania"), LUXEMBOURG("LU", "Luxembourg"), LATVIA("LV", "Latvia"), LIBYA("LY", "Libya"),
			MOROCCO("MA", "Morocco"), MONACO("MC", "Monaco"), MOLDOVA("MD", "Moldova"), MONTENEGRO("ME", "Montenegro"),
			SAINT_MARTIN("MF", "Saint Martin"), MADAGASCAR("MG", "Madagascar"),
			MARSHALL_ISLANDS("MH", "Marshall Islands"), MACEDONIA("MK", "Macedonia"), MALI("ML", "Mali"),
			MYANMAR("MM", "Myanmar"), MONGOLIA("MN", "Mongolia"), MACAO("MO", "Macao"),
			NORTHERN_MARIANA_ISLANDS("MP", "Northern Mariana Islands"), MARTINIQUE("MQ", "Martinique"),
			MAURITANIA("MR", "Mauritania"), MONTSERRAT("MS", "Montserrat"), MALTA("MT", "Malta"),
			MAURITIUS("MU", "Mauritius"), MALDIVES("MV", "Maldives"), MALAWI("MW", "Malawi"), MEXICO("MX", "Mexico"),
			MALAYSIA("MY", "Malaysia"), MOZAMBIQUE("MZ", "Mozambique"), NAMIBIA("NA", "Namibia"),
			NEW_CALEDONIA("NC", "New Caledonia"), NIGER("NE", "Niger"), NORFOLK_ISLAND("NF", "Norfolk Island"),
			NIGERIA("NG", "Nigeria"), NICARAGUA("NI", "Nicaragua"), NETHERLANDS("NL", "Netherlands"),
			NORWAY("NO", "Norway"), NEPAL("NP", "Nepal"), NAURU("NR", "Nauru"), NIUE("NU", "Niue"),
			NEW_ZEALAND("NZ", "New Zealand"), OMAN("OM", "Oman"), PANAMA("PA", "Panama"), PERU("PE", "Peru"),
			FRENCH_POLYNESIA("PF", "French Polynesia"), PAPUA_NEW_GUINEA("PG", "Papua New Guinea"),
			PHILIPPINES("PH", "Philippines"), PAKISTAN("PK", "Pakistan"), POLAND("PL", "Poland"),
			SAINT_PIERRE_AND_MIQUELON("PM", "Saint Pierre And Miquelon"), PITCAIRN("PN", "Pitcairn"),
			PUERTO_RICO("PR", "Puerto Rico"), PALESTINE("PS", "Palestine"), PORTUGAL("PT", "Portugal"),
			PALAU("PW", "Palau"), PARAGUAY("PY", "Paraguay"), QATAR("QA", "Qatar"), REUNION("RE", "Reunion"),
			ROMANIA("RO", "Romania"), SERBIA("RS", "Serbia"), RUSSIA("RU", "Russia"), RWANDA("RW", "Rwanda"),
			SAUDI_ARABIA("SA", "Saudi Arabia"), SOLOMON_ISLANDS("SB", "Solomon Islands"),
			SEYCHELLES("SC", "Seychelles"), SUDAN("SD", "Sudan"), SWEDEN("SE", "Sweden"), SINGAPORE("SG", "Singapore"),
			SAINT_HELENA("SH", "Saint Helena"), SLOVENIA("SI", "Slovenia"),
			SVALBARD_AND_JAN_MAYEN("SJ", "Svalbard And Jan Mayen"), SLOVAKIA("SK", "Slovakia"),
			SIERRA_LEONE("SL", "Sierra Leone"), SAN_MARINO("SM", "San Marino"), SENEGAL("SN", "Senegal"),
			SOMALIA("SO", "Somalia"), SURINAME("SR", "Suriname"), SOUTH_SUDAN("SS", "South Sudan"),
			SAO_TOME_AND_PRINCIPE("ST", "Sao Tome And Principe"), EL_SALVADOR("SV", "El Salvador"),
			SINT_MAARTEN_DUTCH_PART("SX", "Sint Maarten (Dutch part)"), SYRIA("SY", "Syria"),
			SWAZILAND("SZ", "Swaziland"), TURKS_AND_CAICOS_ISLANDS("TC", "Turks And Caicos Islands"),
			CHAD("TD", "Chad"), FRENCH_SOUTHERN_TERRITORIES("TF", "French Southern Territories"), TOGO("TG", "Togo"),
			THAILAND("TH", "Thailand"), TAJIKISTAN("TJ", "Tajikistan"), TOKELAU("TK", "Tokelau"),
			TIMOR_LESTE("TL", "Timor-Leste"), TURKMENISTAN("TM", "Turkmenistan"), TUNISIA("TN", "Tunisia"),
			TONGA("TO", "Tonga"), TURKEY("TR", "Turkey"), TRINIDAD_AND_TOBAGO("TT", "Trinidad and Tobago"),
			TUVALU("TV", "Tuvalu"), TAIWAN("TW", "Taiwan"), TANZANIA("TZ", "Tanzania"), UKRAINE("UA", "Ukraine"),
			UGANDA("UG", "Uganda"), UNITED_STATES_MINOR_OUTLYING_ISLANDS("UM", "United States Minor Outlying Islands"),
			UNITED_STATES("US", "United States"), URUGUAY("UY", "Uruguay"), UZBEKISTAN("UZ", "Uzbekistan"),
			VATICAN("VA", "Vatican"), SAINT_VINCENT_AND_THE_GRENADINES("VC", "Saint Vincent And The Grenadines"),
			VENEZUELA("VE", "Venezuela"), BRITISH_VIRGIN_ISLANDS("VG", "British Virgin Islands"),
			U_S__VIRGIN_ISLANDS("VI", "U.S. Virgin Islands"), VIETNAM("VN", "Vietnam"), VANUATU("VU", "Vanuatu"),
			WALLIS_AND_FUTUNA("WF", "Wallis And Futuna"), SAMOA("WS", "Samoa"), YEMEN("YE", "Yemen"),
			MAYOTTE("YT", "Mayotte"), SOUTH_AFRICA("ZA", "South Africa"), ZAMBIA("ZM", "Zambia"),
			ZIMBABWE("ZW", "Zimbabwe");

			private String isoTag;
			private String name;

			Country(String isoTag, String name) {
				this.isoTag = isoTag;
				this.name = name;
			}

			/**
			 * Gets the name of the country.
			 *
			 * @return The name of the country.
			 */
			public String getCountryName() {
				return name;
			}

			/**
			 * Gets the iso tag of the country.
			 *
			 * @return The iso tag of the country.
			 */
			public String getCountryIsoTag() {
				return isoTag;
			}

			/**
			 * Gets a country by it's iso tag.
			 *
			 * @param isoTag The iso tag of the county.
			 * @return The country with the given iso tag or <code>null</code> if unknown.
			 */
			public static Country byIsoTag(String isoTag) {
				for (Country country : Country.values()) {
					if (country.getCountryIsoTag().equals(isoTag)) {
						return country;
					}
				}
				return null;
			}

			/**
			 * Gets a country by a locale.
			 *
			 * @param locale The locale.
			 * @return The country from the giben locale or <code>null</code> if unknown
			 *         country or if the locale does not contain a country.
			 */
			public static Country byLocale(Locale locale) {
				return byIsoTag(locale.getCountry());
			}

		}

	}
}
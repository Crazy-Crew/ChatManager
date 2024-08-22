### Added:
- Support for Folia.
- MiniMessage support, which is enabled by default in `Vital/chatmanager-config.yml`. You can find a tool to migrate below.
  - https://toolbox.helpch.at/converters/legacy/minimessage
  - Note: If you willingly decide to use legacy color codes, you will not be able to use multiple features.

### Fixed:
- Issue with placeholders like {prefix} not being replaced on auto broadcast.
- Added null safety to /reply, fixing an NPE if no recipient found.
- Issue where all players could see /staffchat, Added a permission check: `chatmanager.staffchat`
- Issue where {player} wasn't replaced for /staffchat in console.
- Issue where if you sent a message in staffchat, It wouldn't show your name but the person receiving it.

### Changes:
- PlaceholderAPI was not parsing placeholders in the broadcast/autobroadcast features.
- Overhauled how commands function, they should be less weird.
- Overhauled configuration files, they will now auto-update as time goes on.
  - As such, /chatmanager debug is no longer needed.
- Messages.yml is now messages.yml, You only have to lowercase the file.
- Rules have been lifted out of `config.yml` into `rules.yml` with a different format!
- BossBar only works as a feature, if you use MiniMessage.
- Log Files have completely changed, they are no longer in the `Logs` folder
  - Log Files are no longer .txt files, they are .log files much like the root .log file.
  - Every restart/plugin reload and every 4 hours, the log files will zip up and compress, to start all over again.
  - This avoids the files getting to large!
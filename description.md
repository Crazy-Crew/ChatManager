<center>

[![chatmanager](https://raw.githubusercontent.com/RyderBelserion/Assets/main/crazycrew/webp/ChatManagerWebsite.webp)](https://github.com/Crazy-Crew/ChatManager)

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

![Paper](https://cdn.jsdelivr.net/gh/intergrav/devins-badges/assets/compact/supported/paper_vector.svg)
![Purpur](https://cdn.jsdelivr.net/gh/intergrav/devins-badges/assets/compact/supported/purpur_vector.svg)

  <p>
    An advanced chat management plugin.
    <br />
    <a href="https://docs.crazycrew.us/docs/category/chatmanager/"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/Crazy-Crew/ChatManager/issues">Report Bug</a>
    ·
    <a href="https://github.com/Crazy-Crew/ChatManager/issues">Request Feature</a>
    ·
    <a href="https://discord.gg/badbones-s-live-chat-182615261403283459">Get Support</a>
  </p>

</center>
<br></br>
<center>

[![Join us on Discord](https://discord.com/api/guilds/182615261403283459/widget.png?style=banner2)](https://discord.gg/badbones-s-live-chat-182615261403283459)
<br></br>

</center>

## Roadmap
A working plugin, We fix any issues that come along.

## API
<details>
  <summary>Maven</summary>

   ```
   <repository>
      <id>crazycrew-releases</id>
      <name>CrazyCrew Repository</name>
      <url>https://repo.crazycrew.us/releases</url>
   </repository>
  ```
  ```
  <dependency>
    <groupId>me.h1dd3nxn1nja.chatmanager</groupId>
    <artifactId>chatmanager</artifactId>
    <version>4.0.2</version>
    <scope>provided</scope>
   </dependency>
  ```

</details>

<details>
  <summary>Gradle (Groovy)</summary>

  ```
  repositories {
     maven {
        url = "https://repo.crazycrew.us/releases"
     }
  }
  ```

   ```
   dependencies {
      compileOnly "me.h1dd3nxn1nja.chatmanager:chatmanager:4.0.2"
   }
   ```
</details>

<details>
  <summary>Gradle (Kotlin)</summary>

  ```
  repositories {
      maven("https://repo.crazycrew.us/releases")
   }
   ```

   ```
   dependencies {
      compileOnly("me.h1dd3nxn1nja.chatmanager:chatmanager:4.0.2")
   }
   ```

</details>

## Information
[Commands/Permissions](https://docs.crazycrew.us/docs/chatmanager/info/commands/permissions)

[Dependencies](https://docs.crazycrew.us/docs/chatmanager/info/plugin-support)

## Features
 <details>
   <summary>Anti-Advertising</summary>
      * Block players from advertising other servers in chat, commands, and on signs.<br>
      * Staff gets notified when someone advertises.<br>
      * A command can be executed on the player that advertises.<br>
      * Whitelist URLs so those URLs wont be blocked when players use them in chat, commands, or on signs.<br>
      * Increase sensitivity option to increase the sensitivity of the anti-advertising checker, can cause false positives.<br>
</details>

 <details>
   <summary>Anti-Bot Attack</summary>
      * Block chat and commands until player moves on join.<br>
</details>
 <details>
   <summary>Anti-Caps</summary>
      * Prevent players from using caps in chat or in commands.<br>
      * Customize minimum message length.<br>
      * Cap percentage.<br>
      * Sets the players message to lowercase.<br>
</details>

 <details>
   <summary>Anti-Spam</summary>
      * Blocks players from repeating the same chat message and the same commands.<br>
      * Delay between sending messages meaning you have to wait X seconds before sending another message.<br>
      * Delay between sending commands meaning you have to wait X seconds before sending another command.<br>
</details>

 <details>
   <summary>Anti-Swear</summary>
      * Block swear words in chat, commands, and on signs.<br>
      * Blocks words together (Example: YouAreAPieceOfCrap).<br>
      * Blocks spaces between words (Example: C r a p).<br>
      * Increase sensitivity option to increase the sensitivity of the anti-swear checker, can cause false positives.<br>
      * Add and remove swear words in game!<br>
      * Staff gets notified when a player swears.<br>
      * A command can be executed on the player that swears.<br>
      * Add as many commands as you like.<br>
      * Whitelist commands, so players can swear in those commands without the message being blocked.<br>
      * Whitelist words that won't be detected by the anti-swear if increase sensitivity is enabled.<br>
      * Option to block the message from being sent to chat.<br>
</details>

 <details>
   <summary>Anti-Unicode</summary>
      * Prevents players from typing special characters in chat. (Example: Ω≈ç√∫˜µ≤≥)<br>
      * Prevents players from typing fancy chat messages from hack clients.<br>
      * This prevents players from bypassing the Anti-Advertising checker and Anti-Swear checker.<br>
      * Staff gets notified when someone uses special characters in chat.<br>
      * A command can be executed to the player that uses special characters in chat.<br>
      * A Unicode whitelist so players can send those messages in chat and their message wont be blocked.<br>
</details>

 <details>
   <summary>Auto-Broadcast</summary>
      * Edit auto broadcast messages in the AutoBroadcast.yml file.<br>
      * The auto-broadcast feature can send messages globally.<br>
      * The auto-broadcast feature can send messages per-world.<br>
      * The auto-broadcast feature can send messages via action bar.<br>
      * The auto-broadcast feature can send messages via title.<br>
      * The auto-broadcast feature can send messages via boss bar.<br>
      * Each option has it's own section so you can...<br>
         <ul><li>Enable or disable each section.
         <li>Set a custom prefix for each section.
         <li>Set a custom interval for each section.
         <li>Enable or disable the header footer for global and per world messages.
         <li>Set a custom header and footer for global and per world messages.</ul>
      * You can add broadcast messages in-game.<br>
      * You can view a list of all the broadcast messages in-game.<br>
      * You can create a new auto broadcast world in-game.<br>
      * Sounds can be played when messages are sent.<br>
      * Set boss bar messages to be permanent by adding the bar delay to -1.
</details>

 <details>
   <summary>Banned Commands</summary>
      * Deny players from using specific commands.<br>
      * Add and remove commands in game.<br>
      * Staff gets notified when a player uses a banned command.<br>
      * A command can be executed on the player that uses a banned command.<br>
</details>

 <details>
   <summary>Broadcast Commands</summary>
      * /Broadcast {message} command<br>
         <ul><li>Custom default prefix
         Custom default chat color
         A sound is played to everyone on the server when you broadcast a message.</ul>
      * /Annoucement {message} command<br>
         <ul><li>Broadcast an announcement message to the server.
         Custom Header/ Footer
         A sound is played to everyone on the server when you announce a message.</ul>
      * /Warning {message} command<br>
         <ul><li>Broadcast a warning message to the server.
         Custom Header/ Footer
         A sound is played to everyone on the server when you send out a warning message.</ul>
</details>

 <details>
   <summary>Banned Commands</summary>
      * Per-group chat format.<br>
      * Players will automatically be placed in their chat format group, depending on what permission group they are in.<br>
      * Players will be place in the default format section if their permissions group isn't in the config.<br>
      * Learn more on how to use the chat format <a href="https://github.com/Crazy-Crew/ChatManager/wiki/Chat-Format">here</a><br>
      * <a href="https://github.com/ChatManager/ChatManager/wiki/Placeholders">Available Placeholders</a><br>
</details>

 <details>
   <summary>Chat Color</summary>
      * You can send color codes in chat.
      * Permission to use the color codes and formats.<br>
      * Hex color codes supported for almost every message.<br>
</details>

 <details>
   <summary>Banned Commands</summary>
      * Chat Radius chat channels.<br>
      * Local chat, Global chat, and World chat.<br>
      * In local chat players can only talk to other players that are in the customizable range.<br>
      * In global chat players can send messages to everyone on the server.<br>
      * In world chat players can only send messages to players that are in the same world as them.<br>
      * Add the placeholder {radius} or %chatmanager_radius% (PAPI placeholder) to the chat format to show what chat channel the players are in.<br>
      * Spy mode can be used to see what everyone is saying in chat no matter what.<br>
      * You can set it so spy mode enables when specific players join the server.<br>
      * You can set which chat channel players join when they join the server.<br>
      * The command to switch chat channels is /chatradius.<br>
      * Players can use a customizable symbol to talk to players in local chat.<br>
      * Players can use a customizable symbol to talk to players in global chat.<br>
      * Players can use a customizable symbol to talk to players in world chat.<br>
      * Learn more on how to use the chat radius <a href="https://github.com/Crazy-Crew/ChatManager/wiki/Chat-Radius">here</a><br>
</details>

 <details>
   <summary>Clear Chat</summary>
      * Clears all online players chat with a custom broadcast message.<br>
      * Bypass permission so players wont have their chat cleared on command.<br>
</details>

 <details>
   <summary>Command Spy</summary>
      * You can see what commands everyone is sending on the server.<br>
      * Custom command spy format.<br>
      * Bypass permission so staff can't see what commands you type.<br>
</details>

 <details>
   <summary>Custom Messages</summary>
      * Custom Join, Quit, and First Join messages.<br>
      * Custom join and first join action bar messages.<br>
      * Custom join and first join titles.<br>
      * Per group Join and Quit messages.<br>
      * Play sound when players join or leave the server.<br>
      * Customizable title delays.<br>
      * Custom Message of the Day (MOTD).
</details>

 <details>
   <summary>Lists</summary>
      * Player list to show all the players that are currently online.<br>
      * Staff list to show all the staff members that are currently online.
</details>

 <details>
   <summary>Logs</summary>
      * Logs everything typed in chat.<br>
      * Logs every command executed.<br>
      * Logs every message that is written on a sign.<br>
      * Logs when a player advertises.<br>
      * Logs when a player curses.<br>
      * Add commands in the config that wont be logged.
</details>

 <details>
   <summary>Mentions</summary>
      * When a player is mentioned in chat, they will receive a sound notification as well as a title message.<br>
      * Players can be mentioned by a customizable symbol that can be changed in the config.yml. (Default is @{player})<br>
      * You can mention everyone on the server by doing @everyone.
</details>

 <details>
   <summary>Mute Chat</summary>
      * Prevent players from talking in chat.<br>
      * Prevents specific commands from being executed when chat is disabled.<br>
      * Bypass permission so staff can talk in chat.<br>
      * MuteChat -s command so staff can only see when chat is muted.
</details>

 <details>
   <summary>Per-World Chat</summary>
      * If enabled (Disabled by default) players can only talk to other players that are in the same world as them.<br>
      * Group worlds for per world chat. You can group worlds together so players in the grouped worlds can talk to each other.<br>
      * Bypass command: /Perworldchat Bypass (/pwc bypass)<br>
      <ul><li>When the command is executed, everyone can see what you type in chat, and you can see what everyone else types in chat.</li></ul>
</details>
 <details>
   <summary>Ping Command</summary>
      * You can check your current ping anytime on the server.<br>
      * You can check other players ping anytime on the server.<br>
      * This is the one and only thing that cant be customized.
</details>

 <details>
   <summary>Placeholders</summary>
      * Chat Manager has multiple placeholders built into the plugin by default that you can use in almost every message chat manager has to offer, you can view a full list of all the available placeholders <a href="https://github.com/Crazy-Crew/ChatManager/wiki/Placeholders">here</a><br>
      * Chat Manager also supports the use of PlaceholderAPI placeholders in almost every message chat manager has to offer! Please visit <a href="https://github.com/PlaceholderAPI/PlaceholderAPI/wiki">PlaceholderAPI Wiki</a> to help you get started to learn how to get placeholders from the plugin!
</details>

 <details>
   <summary>Private Message</summary>
      * /message and /reply commands.<br>
      * Custom message format.<br>
      * A sound is played to the person that receives a private message.<br>
      * Players cant private message staff if they are vanished (Works with Essentials Vanish and Super Vanish).<br>
      * When a player is ignored they wont be able to send private messages to the player that's ignoring them (only works with essentials ignore).<br>
      * Hover text for private messages and suggest command.
</details>

 <details>
   <summary>Server Rules</summary>
      * /Rule Command<br>
      * Rule pages. You can add as many pages as you'd like in the config.
</details>

 <details>
   <summary>Sign Colors</summary>
      * You can use color codes on signs.<br>
      * Permission to allow players to either use color codes or format codes.
</details>

 <details>
   <summary>Social Spy</summary>
      * You can spy on players private messages.<br>
      * Bypass permission to players cant spy on you.
</details>

 <details>
   <summary>Staff Chat</summary>
      * Talk to all online staff members secretly from the rest of the server.
      * Custom staff chat format.<br>
      * Boss bar can be permanently enabled while staff chat is enabled.
</details>

 <details>
   <summary>More Info</summary>
      * Almost everything can be enabled or disabled in the config.yml. Everything besides commands.<br>
      * Almost every message can be customized in the messages.yml.<br>
      * There is a bypass permission for almost everything.<br>
      * Thousands of Placeholders are available with PlaceholderAPI installed.
</details>

[contributors-shield]: https://img.shields.io/github/contributors/Crazy-Crew/ChatManager.svg?style=flat&logo=appveyor
[contributors-url]: https://github.com/Crazy-Crew/ChatManager/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/Crazy-Crew/ChatManager.svg?style=flat&logo=appveyor
[forks-url]: https://github.com/Crazy-Crew/ChatManager/network/members
[stars-shield]: https://img.shields.io/github/stars/Crazy-Crew/ChatManager.svg?style=flat&logo=appveyor
[stars-url]: https://github.com/Crazy-Crew/ChatManager/stargazers
[issues-shield]: https://img.shields.io/github/issues/Crazy-Crew/ChatManager.svg?style=flat&logo=appveyor
[issues-url]: https://github.com/Crazy-Crew/ChatManager/issues
[license-shield]: https://img.shields.io/github/license/Crazy-Crew/ChatManager.svg?style=flat&logo=appveyor
[license-url]: https://github.com/Crazy-Crew/ChatManager/blob/main/LICENSE
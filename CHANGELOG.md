### Removed:
- Vault is no longer needed to run the plugin, All messages have `PlaceholderAPI` support.
- Per-group chat format has been removed from the `config.yml` as you can simply use `PlaceholderAPI` and `Group Weights`.
- Hex Color Format can no longer be changed, It did not function very well.
- `Config_Version` from the `config.yml`.
- `Metrics_Enabled` from the `config.yml` as you can disable Metrics in `bStats`.
- `{display_name}`, `{displayname}`, `{world}`, `{online}`, `{ess_player_balance}`, `{ess_player_nickname}`. You can use `PlaceholderAPI` for literally everything.

### Changed:
- Updated `{vault_prefix}` and `{vault_suffix}` to `%luckperms_prefix%` and `%luckperms_suffix%`.
- Vanish Support has been altered, We no longer detect plugins itself but use hopefully a universal method to detect if vanished.
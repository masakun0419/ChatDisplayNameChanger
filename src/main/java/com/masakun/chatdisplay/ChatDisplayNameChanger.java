package com.masakun.chatdisplay;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * チャット表示名を変更するためのメインクラスです。
 * Bukkit / Paper サーバーに読み込まれるエントリーポイントとなります。
 */
public class ChatDisplayNameChanger extends JavaPlugin {

    /**
     * プレイヤーごとの表示名を管理するマネージャークラスです。
     */
    private DisplayNameManager displayNameManager;

    /**
     * プラグイン有効化時に呼び出されます。
     * ここでリスナーやコマンドの登録を行います。
     */
    @Override
    public void onEnable() {
        // コンソールにプラグインが有効化されたことを出力
        getLogger().info("ChatDisplayNameChanger enabled!");

        // 表示名を管理するマネージャーを初期化
        displayNameManager = new DisplayNameManager();

        // チャットイベントのリスナーを登録
        // プレイヤーがチャットしたときに、ここで設定された表示名に置き換えます
        getServer().getPluginManager().registerEvents(
            new ChatListener(displayNameManager), this
        );

        // /cdname コマンドを登録
        // plugin.yml にコマンドが定義されていない場合はエラーを出力します
        if (getCommand("cdname") != null) {
            getCommand("cdname").setExecutor(new NameCommand(displayNameManager));
        } else {
            getLogger().severe("Command 'cdname' is not defined in plugin.yml");
        }
    }
}

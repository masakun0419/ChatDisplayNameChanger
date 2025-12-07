package com.masakun.chatdisplay;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * チャットイベントをフックし、プレイヤーの表示名を書き換えるリスナークラスです。
 */
public class ChatListener implements Listener {

    /**
     * プレイヤーのカスタム表示名を取得するためのマネージャー。
     */
    private final DisplayNameManager displayNameManager;

    /**
     * コンストラクタ。
     *
     * @param manager 表示名を管理するマネージャー
     */
    public ChatListener(DisplayNameManager manager) {
        this.displayNameManager = manager;
    }

    /**
     * プレイヤーがチャットしたときに呼び出されるイベントハンドラです。
     * ここで、カスタム表示名が設定されていればチャット表示をそれに置き換えます。
     *
     * @param event 非同期チャットイベント
     */
    @EventHandler
    public void onChat(AsyncChatEvent event) {

        // チャットを送信したプレイヤーを取得
        var player = event.getPlayer();
        var uuid = player.getUniqueId();

        // プレイヤーに紐づくカスタム表示名を取得（なければ null）
        String customName = displayNameManager.getName(uuid);

        // カスタム表示名が設定されている場合のみ、表示を書き換える
        if (customName != null && !customName.isEmpty()) {
            // Adventure API の renderer を使って、
            // 「カスタム名: メッセージ」の形式でチャットを表示
            event.renderer((source, sourceDisplayName, message, viewer) ->
                Component.text(customName + ": ").append(message)
            );
        }
    }
}

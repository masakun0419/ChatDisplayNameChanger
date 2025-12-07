package com.masakun.chatdisplay;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;

public class NameCommand implements CommandExecutor {

    private final DisplayNameManager manager;

    public NameCommand(DisplayNameManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // プレイヤー以外（コンソールなど）からの実行を禁止
        if (!(sender instanceof Player player)) {
            sender.sendMessage("\u3053\u306e\u30b3\u30de\u30f3\u30c9\u306f\u30d7\u30ec\u30a4\u30e4\u30fc\u306e\u307f\u5b9f\u884c\u3067\u304d\u307e\u3059\u3002");
            return true;
        }

        // 引数が不足している場合は、使い方を案内
        if (args.length < 1) {
            player.sendMessage("\u4f7f\u7528\u65b9\u6cd5: /cdname <\u65b0\u3057\u3044\u8868\u793a\u540d>");
            player.sendMessage("\u5143\u306e\u540d\u524d\u306b\u623b\u3059\u306b\u306f: /cdname reset");
            return true;
        }

        //cdname reset → 元の名前に戻す
        if (args[0].equalsIgnoreCase("reset")) {

            String defaultName = player.getName();  // 元の Minecraft 名

            manager.setName(player.getUniqueId(), defaultName); // 保存名も初期化
            player.displayName(Component.text(defaultName));    // チャット表示名だけ戻す

            player.sendMessage(Component.text(
                "\u8868\u793a\u540d\u3092 '" + defaultName + "' \u306b\u623b\u3057\u307e\u3057\u305f\u3002"
            ));
            return true;
        }

        // 1 つ目の引数を新しい表示名として扱う
        String newName = args[0];

        // プレイヤーの UUID に対して新しい表示名を保存
        manager.setName(player.getUniqueId(), newName);

        // プレイヤーに変更完了のメッセージを送信
        player.sendMessage(Component.text(
            "\u30c1\u30e3\u30c3\u30c8\u8868\u793a\u540d\u3092 '" + newName + "' \u306b\u5909\u66f4\u3057\u307e\u3057\u305f\u3002"
        ));

        return true;
    }
}

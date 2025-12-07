package com.masakun.chatdisplay;

import java.util.HashMap;
import java.util.UUID;

/**
 * プレイヤーごとのチャット表示名を管理するクラスです。
 * メモリ上に UUID と表示名の対応を保持します。
 */
public class DisplayNameManager {

    /**
     * プレイヤー UUID とカスタム表示名の対応表。
     * サーバー再起動で消える簡易的な実装です。
     * 永続化したい場合は、Config やデータベースへの保存処理を追加してください。
     */
    private final HashMap<UUID, String> nameMap = new HashMap<>();

    /**
     * 指定したプレイヤー UUID に対して、表示名を設定します。
     *
     * @param uuid プレイヤーの UUID
     * @param name 新しく設定する表示名
     */
    public void setName(UUID uuid, String name) {
        nameMap.put(uuid, name);
    }

    /**
     * 指定したプレイヤー UUID に対応する表示名を取得します。
     * 未登録の場合は null を返します。
     *
     * @param uuid プレイヤーの UUID
     * @return 設定されている表示名、または null
     */
    public String getName(UUID uuid) {
        return nameMap.get(uuid);
    }
}

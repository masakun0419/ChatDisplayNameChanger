# ChatDisplayNameChanger - 仕様書

## 概要
Minecraft Paper 1.20.1 向けのプラグインで、プレイヤーがチャットを送信した際に
**本来のプレイヤー名を任意の表示名へ差し替える**ことができる。

プレイヤー自身がコマンド `/cdname <新しい名前>` を実行することで、
以後のチャット送信時の名前が変更される。

---

## 対象環境
- Server: Paper 1.20.1
- Java: 17
- Build Tool: Maven
- API: Paper API（Adventure 形式のチャット）

---

## 機能仕様

### 1. チャット表示名の上書き
- プレイヤーがチャットを行った際、AsyncChatEvent をフック。
- DisplayNameManager に保持している名前があれば、  
  チャットの送信者名をその表示名に差し替える。
- メッセージ本文は変更しない。

例：  
`player123: こんばんは！`  
→ `/cdname さかなくん` 実行後  
`さかなくん: こんばんは！`

---

### 2. コマンド `/cdname`

#### 使用方法
/cdname <新しい名前>
/cdname reset


#### 詳細
- /cdname reset で元の名前に戻す
- 実行者がプレイヤーでない場合エラーを返す。
- 引数が無い場合は使用方法を案内する。
- 新しい名前を DisplayNameManager に保存する。
- 成功時にプレイヤーへメッセージ表示。

---

## 権限
- 特になし  
（全プレイヤー使用可）
※ 必要であれば `chatdisplaynamechanger.use` など追加可能

---

## 内部仕様

### DisplayNameManager
- UUID → 表示名 のマッピングを保持する。
- メモリのみ保持（サーバー再起動でリセット）。
- 永続化が必要であれば拡張可能（YML/JSON/SQLite など）。

### ChatListener
- AsyncChatEvent の renderer を上書き。
- displayNameManager に名前があればカスタム名を使用して描画。
- 無ければ通常の Bukkit 名を用いる。

### NameCommand
- `/cdname` のロジックを処理。
- 新しい名前の登録・保存。

---

## 今後の拡張余地（任意）
- 色コード（例：`&a緑の名前`）対応
- 設定ファイルへの永続化
- 権限で利用制限
- 管理者が他人の名前を変更できる機能
- プレイヤー一覧の表示名上書き
- タブ補完対応

---
## ディレクトリ構成
```
ChatDisplayNameChanger/
├─ src/
│ ├─ main/
│ │ ├─ java/
│ │ │ └─ com/example/
│ │ │ ├─ ChatDisplayNameChanger.java
│ │ │ ├─ ChatListener.java
│ │ │ ├─ NameCommand.java
│ │ │ └─ DisplayNameManager.java
│ │ └─ resources/
│ │ └─ plugin.yml
└─ pom.xml
```
---

## plugin.yml

```yaml
name: ChatDisplayNameChanger
version: 1.0
main: com.example.ChatDisplayNameChanger
api-version: 1.20
commands:
  cdname:
    description: Change your chat display name
    usage: /cdname <name>

ビルド方法（Maven）
mvn package
生成物：
target/ChatDisplayNameChanger-1.0-SNAPSHOT.jar
Paper サーバーの plugins/ に入れて起動する。
```

## テスト手順
①サーバー起動

②/cdname さかなくん

③チャットで表示名が変わるか確認

④サーバー再起動すると元の名前に戻る（仕様）
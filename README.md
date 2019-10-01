# 概要
SpringBootプロジェクトにおけるRestController、Service、Repository、ModelのScaffoldツールです。

※ORMは、SpringData（JPA）を利用しています。

# 実行環境

- Java8以上

# 準備

1. このリポジトリをクローンします。
2. binフォルダをパスに通します。

# 使い方 

以下のコマンドを実行すると、カレントディレクトリに作成されます。

```
sbcui -p jp.co.sample -c "User[id:Long,name:String]"
```
※クラス情報はダブルクォートで囲んでください。

|option|value|
|:--|:--|
|-p|パッケージ名|
|-c|クラス情報|

### クラス情報の書き方

クラス名[フィールド名:Javaのデータ型,...]

※クラス情報内に「id」は必ず含めてください。

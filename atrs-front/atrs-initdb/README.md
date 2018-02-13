データソースのルーティングを使用する場合は、以下のプロファイルでDBを作成する。


1.データベースを3種類作成する
* CREATE DATABASE atrs WITH ENCODING = 'UTF8';
* CREATE DATABASE atrs1 WITH ENCODING = 'UTF8';
* CREATE DATABASE atrs2 WITH ENCODING = 'UTF8';


2.以下のプロファイルを実行する。
* mvn sql:execute -P {プロファイルの種類}
※ プロファイルの種類は以下の通り。
	- local		個人のPC環境
	- dev		データベースがAWS
	- ci		ci環境
	- spstest	
* 個人のPC環境の例
	- mvn sql:execute -P local
	- mvn sql:execute -P local1
	- mvn sql:execute -P local2

3.以下のmavenビルドを実行する。(DynamoDB、Redisの初期化)
* mvn clean compiler:compile antrun:run -P {プロファイルの種類}

※ Jenkinsでは、パラメータ[PROFILE]にプロファイルの種類を設定する。但し、2017/1/13現在はciのみ動作確認済。

※ 以下、参照

* atrs-initdb/pom.xm


## サンプル学習用Githubのクライアントアプリです。
Kotlin/Javaでブランチを分けています。

## [MVVM( Model-View-ViewModel )設計](https://ja.wikipedia.org/wiki/Model_View_ViewModel)
***

ざっくり言うと、MVVMは上記MVCの派生パターンであり、MVVMを考慮してアプリケーションを開発する目的は、他のMVC系のパターンと同様にアプリケーションの「プレゼンテーション層(ユーザーが見て触れられる層)とドメイン(ビジネスロジック)を分離」することです。

そのアーキテクチャをAndroidで[DataBinding](https://developer.android.com/topic/libraries/data-binding/?hl=ja)ライブラリが登場したことで、利用可能になったよと、そして、[ Android Architecture Components ](https://developer.android.com/topic/libraries/architecture/)の登場により、より扱いやすくなったよ、ということのようです。

大まかなメリットとしては、よくあげられるのは

- 関心の分離
- 依存関係の切り離し
- 画面回転問題

などなど、また、依存関係が

**Model**
(リモートとローカル問わず、データリソースを操作する領域)
***

⇩

**ViewModel**
(DataBindingライブラリを通し、Viewに表示するデータの監視、取得をする領域)
***

⇩

**View**
(xml/Activity/Fragment)
***

と依存関係が単方向になることで、保守性も向上します。
設計についてはもっと詳しい記事が世の中に溢れているので、詳細は以下に紹介いたします。

主要な設計への理解に関しては、以下記事を参考。

**[Androidアーキテクチャことはじめ ― 選定する意味と、MVP、Clean Architecture、MVVM、Fluxの特徴を理解する](https://employment.en-japan.com/engineerhub/entry/2018/01/17/110000)**

設計参考書籍。

**[Android アプリ設計パターン入門](https://peaks.cc/books/architecture_patterns)**

## [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/)
***

Android Architecture ComponentsはGoogleが推奨するデザインパターンを扱いやすくしたライブラリ群です。
4つに分かれてます。([公式ドキュメント](https://developer.android.com/topic/libraries/architecture/)から参照/引用)

- [**Lifecycle** (ライフサイクルイベントに自動的に応答するUIを作成)](https://developer.android.com/topic/libraries/architecture/lifecycle)
- [**LiveData** (基礎となるデータベースが変更されたときにビューに通知するデータオブジェクトを構築)](https://developer.android.com/topic/libraries/architecture/livedata)
- [**ViewModel** (アプリの回転で破棄されないUI関連のデータを保存)](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [**Room** (アプリ内オブジェクトとコンパイル時のチェックを使用して、アプリのSQLiteデータベースにアクセス)](https://developer.android.com/topic/libraries/architecture/room)



今回お世話になる、[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)はModelの変更を監視し、データをViewにバインドし、View操作を伝達するクラスです。Viewとライフサイクルを共に歩むので、画面回転などユーザの想定外の操作に強くなります。

[LiveData](https://developer.android.com/topic/libraries/architecture/livedata)は、[Android Architecture Components](https://developer.android.com/topic/libraries/architecture/)が提供する、ライフサイクルと連動した監視が可能な、データホルダーのクラスです。

**[【Android Architecture Components】Guide to App Architecture 和訳](https://qiita.com/oya-t/items/e4f65ec42de9ee65d6f1)**

**[LiveDataの基礎的な性質を整理する。](https://qiita.com/nukka123/items/f7af4445d737c821c24c)**

### 当サンプル
***

参考記事：**[MVVM architecture, ViewModel and LiveData (Part 1)](https://proandroiddev.com/mvvm-architecture-viewmodel-and-livedata-part-1-604f50cda1)**

ここで紹介されているサンプル(よく見るあるユーザのGithubリポジトリをずらっと表示するだけのクライアントアプリ)の実装を、真似してます。

これだけ

[![https://gyazo.com/cb86e446446cafc7043b46cabb630c3e](https://i.gyazo.com/cb86e446446cafc7043b46cabb630c3e.gif)](https://gyazo.com/cb86e446446cafc7043b46cabb630c3e)


まず、当たり前ですが、依存関係のない方向に従い **( Model-> ViewModel -> View )** の順で実装していきましょう。

設計にこれといった答えはなく、これが正解というわけではないです。色々なサンプルを触って、最もシンプルでわかりやすかったので、あくまで一例です。

## **イメージ**
***

![Untitled mvvmDiagram (1).png](https://qiita-image-store.s3.amazonaws.com/0/187392/baa44dbe-c9bf-6f77-66c0-c5501bc74112.png)


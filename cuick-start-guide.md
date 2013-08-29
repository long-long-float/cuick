#Cuickスタートガイド

###まずはhello worldから

hello.cuick

    puts("hello cuick!");

コンパイル

    $ cuickc hello.cuick

コンパイルすると、hello.cppが同じディレクトリにできます。そのまま、g++などのコンパイラでコンパイル出来ます。

###hello.cuickの解説

Cuickは、スクリプト言語のようにいきなり文を書いてもいいし、いつものC++のようにmain関数の中に書いてもいいです。  
putsはcuickで用意された文です。与えられた引数を出力します。

###C++のコンパイルを同時に行う

毎回C++にコンパイルしてからそれをまた実行形式にコンパイルするのはとても面倒です。cuickcでは`-w`オプションで一気に実行形式ファイルにコンパイルできます。  
`-w`オプションでコンパイルするには、`config.yaml`を実行環境に合わせて編集する必要があります。

config.yamlの設定例

    !!jp.long_long_float.cuick.compiler.Config
    compiler: [g++, -o, @{FILE_BASENAME}.exe, @{DEST_FILE}]
    test:
        exefile: @{FILE_BASENAME}.exe

1行目:編集しないでください  
2行目:C++コンパイラのコマンドラインを指定します。@{XXX}はコンパイル時に展開される定数です。  
3行目~:自動テスト(後述)のための設定です。exefileに実行可能ファイルを指定してください。

実行

    $ cuick -w hello.cuick

同じディレクトリに実行可能ファイルができているはずです。

###config.yamlの定数一覧

- FILE_BASENAME

    ファイル名から拡張子を除いた部分

- DEST_FILE

    cuickcが生成するC++ファイル。デフォルトでは@{FILE_BASENAME}.cpp

###for-each文

cuickの中心的な機能の1つです。配列はもちろん、std::vectorに代表されるコンテナを巡回できます。さらに型が特定されている場合は省略できます(型が特定できない場合はエラーが出ます)。

foreach

    for([型] 変数名 :[向き] 式 [: 範囲(ポインタの場合)]) 文...

[]で囲まれているものは省略可能です

- 型, 変数名

    ループ変数の型、変数名を指定します

- 向き(>か<)

    ループの向きを指定します。>が正方向で、<が逆方向です。

- 式

    配列やvector、範囲を指定出来ます。配列やvectorの場合、前要素を、範囲の場合は範囲の最初から最後までを巡回します。

ここでいう範囲とは、範囲を表すリテラルのことで、`a..b`は[a, b]、`a...b`は[a, b)(bを含まない)という意味です。

foreach.cuick

    int ary[] = {1, 2, 3, 4, 5};
    for(i : ary) puts(i);           //ポインタではない配列は範囲を省略できます
    for(i : ary : 2..4) puts(i);    //範囲を指定できます
    for(i : ary : 3) puts(i);       //0から3の範囲です

    int *ptr = ary;
    for(i : ptr : 5) puts(i);       //ポインタの場合は範囲の指定は必須です

    for(i : ary){
        for(j : ary){
            print(i * j, "");       //入れ子も可能
        }
        puts();
    }

    for(i : 1..3) puts(i);          //配列だけでなく範囲も指定可能です
    for(i : 10) puts(i);            //0から10の範囲です

    std::vector<int> vec;
    vec.push_back(10);
    vec.push_back(20);
    vec.push_back(30);
    for(itr : vec) puts(itr);       //vectorもok

###@input

競技プログラミングでは、標準入力からの入力が必ずと言っていいほどあり、しかも毎回ほぼ決まった形です。@inputは簡単な入力の定義を書くだけで入力ができてしまいます。例えば整数nに続いて、長さnの数列xを入力する場合、`@input{ n x[n] }`だけで入力ができます。定義の詳細はcuick-doc.mdを参照してください。

at-input.cuick

    //xの合計を出力するプログラム
    int n, x[1000];
    @input{ n x[n] }
    int sum = 0;
    for(i : x:n) sum += i;
    puts(sum);

###@test

プログラムを書いたらテストをしたい時があります。ただ、素のC++ではそのための機構がなく少々面倒です。@testは入力ファイル、出力ファイルを指定するだけでcuickcが自動でテストをしてくれます。

at-test.cuick

    //入力の2乗を出力するプログラム
    @test(0..20){
        in: "in@{id}.txt"
        out: "out@{id}.txt"
    }
    int n;
    @input{n}
    puts(n ** 2);

###演習

実際に問題を解いてみましょう。

###終わりに

以上でCuickスタートガイドは終わりです。まだまだCuickには魅力的な機能があるので、cuick-doc.mdを参照してください。

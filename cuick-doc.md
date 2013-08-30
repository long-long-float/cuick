#cuick

#C++との違い

- unsigned charなど複数のからなる型名は-でつなぎます。(例：unsigned-char)

- classやstructはありませんので、\`\`(built in記法)を使ってください。

#機能

定義の中で

- []は省略可能です

- (a|b|c)はaかbかcです

##range

範囲を表します。a, bは整数です。どこでも使えるわけではなく定義の中でrangeと表記されている所で使えます。また、整数aで置き換えることができその場合は、0...aと等価です。

`a..b`

    [a, b] (a <= x <= bを満たすxの集合)

`a...b`

    [a, b)(a <= x < bを満たすxの集合)

##for

    for([var_type] var_name :[direction] enumerable[ : range]) {...}

###direction

ループの向きを指定します。>は正方向、<は逆方向です。デフォルトでは正方向です

###enumerableに指定可能なもの

- 配列、ポインタ(範囲の指定が必要)

- range

- 整数

    0...nのシンタックスシュガー

- vectorなどのコンテナ

    上のどれも当てはまらなかった場合に自動的にコンテナとみなす(STL以外のコンテナは警告をだす)

##puts, print

    puts(arg, ...)
    print(arg, ...)

argを出力します。argが複数あった場合putsの場合はstd::endlで、printの場合は空白でつないで出力します。

example

    puts 1, 2, 3;
    /*
    1
    2
    3
    */
    print 1, 2, 3; //1 2 3

##var_dump

変数の中身を変数名とともに出力します

    var_dump(var, ...)

##@input

    @input{
        (var|var[range]|var!) ...
    }

定義に従ってコンソール入力します

- var

    1変数

- var!

    0だった場合break

- var[range]

    rangeの範囲で連続で入力

- var[n]

    0...nのrange

example

    int n, x[1000];
    @input{
        n
        x[n]
    }

##@degug

debugモードを有効にします。debugモードが有効だと、

- dump_var

- @test

が使えるようになります。デフォルトではdebugモードは無効で、@debugと書くことによってはじめて上の機能が使えるようになります。

##@test

    @test(range){
        in: in_file
        out: in_file
        ...
    }

プログラム実行時に自動テストを行います。inは入力ファイル名、outは出力ファイル名です。inとoutはいくつでも書け、すべてのinのうち、n番目にあるinとすべてのoutのうち、n番目にあるoutが対応しています。これを書くとコンパイラ実行時、in_fileを標準入力に流し込み、標準出力がout_fileと比較されます。  

example

    @test{
        in: "in1.txt"
        out: "out1.txt"
        in: "in2.txt"
        out: "out2.txt"
    }

rangeを指定すると、rangeの範囲でテストが実行されるようになります。ファイル名の中で@{id}が使えるようになり、rangeの範囲で@{id}が巡回した値に置き換わります。

example

    //上と等価
    @test(1..2){
        in: "in@{id}.txt"
        out: "out@{id}.txt"
    }

##memorize

    memorize(hash, max, init) 関数定義...

大きさmax, 初期値initのvectorを使ってメモ化します。

example

    memorize(n, 1000, -1) int fact(int n){
        n > 0 ? n * fact(n - 1) : 1;
    }

##@while

    @while(condition)

これ以降の文をwhileで囲みます。ネストが深くなるのが嫌な人向けです

##多重代入

    var, ... = (container|var, ...)

左辺値の変数が2つ以上でかつ、2つ以上の式の時に発動します。変数宣言時には使用できません。代入と違い_文_です(つまり式としては使えません)。

example

    int x, y;
    //スワップ
    x, y = x, y;

##tuple

型

    tuple<type, ...>

いちいち構造体やクラスを作るのが面倒臭い時に使えます。N番目の要素はitemNで参照できます。

##べき乗演算子(**)

    left ** right

leftのright乗を表します。実体はstd::powです。

##unless

ifの否定

##built in code

C++のコードを埋め込みたい時に使います。最後のセミコロンを省略すると、main関数の外に配置されます。

    `cout << "This is c++ code!" << endl;`;

###TODO

- foreachのlast

    一番最後に実行されるブロックを指定できる

- block

    関数呼び出し時にブロックを渡すことができる。


#config.yamlについて

C++からコンパイルするためのコマンドラインや、自動テストのための設定をここに記述します。@{XXX}と書くことでコンパイル時に展開されます。

##config.yamlの設定例

    !!jp.long_long_float.cuick.compiler.Config
    compiler: [g++, -o, @{FILE_BASENAME}.exe, @{DEST_FILE}]
    test:
        exefile: @{FILE_BASENAME}.exe

compiler : C++からコンパイルするためのコマンドラインを配列で指定してください

test.exefile : 自動テスト時に呼ばれる実行ファイル名を指定してください

##config.yamlの定数一覧

- FILE_BASENAME

    ファイル名から拡張子を除いた部分

- DEST_FILE

    cuickcが生成するC++ファイル。デフォルトでは@{FILE_BASENAME}.cpp

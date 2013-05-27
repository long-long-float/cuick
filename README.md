cight
=====

#メモ
block埋め込んでいいんじゃね?←blockつき関数は複数箇所で呼び出される

#特徴
- C++と100%の互換性

- 簡潔な構文、豊富なシンタックスシュガー

- クロージャ、ブロック、コルーチン

- 自動delete

- vector, map, tuple, range, 正規表現リテラル

- 拡張メソッド

- ヒアドキュメント

- メタプログラミング（超マクロ）

#字句構造

文の最後の;は不要です

//, /**/はコメントです

#変数

C++と同じように_型名+変数名_とできますが、初期化できる場合には型名をvarとすることで型推論が利用できます(要C++11)

#基本の制御構造

if-else if-else, for, while, do-whileについてはC++と同じです。

##unless

ifの否定

##後置if, unless, for, while

    ... xxx(expression)

##foreach(for STL's container)

    foreach(var in container){
        ...
    }

C++のSTLのコンテナを反復処理します。
    
    var ary = [1, 2, 3]
    var sum = 0
    foreach(e in ary){
        sum += e
    }

は以下と等価です

    vector<int> ary;
    ary.push_back(1);
    ary.push_back(2);
    ary.push_back(3);
    auto sum = 0;
    for(vector<int>::iterator e = ary.begin() ; e != ary.end() ; e++){
        sum += *e;
    }

containerにrangeリテラルを指定すると、varをカウンターとしたfor文になります

##foreach(for native array)

    foreach(var in array:range){
        ...
    }

生配列を反復処理します。rangeを省略すると、0...sizeof(array) / sizeof(array[0])とします

    var ary = raw[1, 2, 3]
    foreach(e in ary:){
        e **= 2
    }

は以下と等価です

    int ary[] = {1, 2, 3};
    for(int i = 0;i < sizeof(ary) / sizeof(ary[0]);i++){
        ary[i] = std::pow(ary[i], 2.0);
    }

#関数

##call

関数呼び出しについては、C++と同じです。ただし、関数の後ろのカッコは省略可能です?_←関数ポインタとの区別がつかない_

##define

基本はC++と同じですが、

- returnは不要

- templateは$を接頭語にする。

以下は

    $T abs($T n){
        n >= 0 ? n : -n
    }

と以下と等価です

    template<class T>
    T abs(T n){
        return n >= 0 ? n : -n;
    }

- ref

refをつけると参照(&)になります

- 指定子

inline, __cdeclをはじめとしたC++のものに加えて、extendがあります

- extend

あたかもクラスにメソッドを追加しているかのようにできます

    extend void int.times(void block(int)){
        for(int i = 0;i < this;i++){
            block(i)
        }
    }

    5.times{ puts it }

は以下と等価です

    void times(int& this, Closure block){
        for(int i = 0;i < this;i++){
            block(i);
        }
    }

    times(5, Closure());

#クロージャ、ブロック、コルーチン

##クロージャ

関数をオブジェクトのように扱えます。もちろん関数の引数にも使えますが、そういう場合にはblockが使えます。

    var sq = ->> int (int n){ n ** 2 }

は、

    //outer
    class Closure{
    public:
        int operator()(int n){
            return pow(n, 2.0);
        }
    };

    auto sq = Closure();

外部変数をクロージャ内で使っている場合は、自動的にメンバ変数として取り込まれます。

テンプレートも使えます

    var abs = ->> $T ($T n){ n >= 0 ? n : -n }

引数が1つの場合は、引数リストを省略でき、テンプレート変数it, $ITで参照できます

    var twice = ->> $IT { it * 2 }

は、以下と等価です

    //クラス定義略
    template<class IT>
    IT operator()(IT it){
        return it * 2;
    }

##ブロック

関数の引数にブロック宣言をすると、関数にブロックを渡すことができます。

関数-define-extendを参照してください

##コルーチン

クロージャ、ブロックの中でreturnの代わりにyieldを使うと、次回呼び出し時そこから処理を再開できる、コルーチン(もどき)として使うことができます。

#クラスとオブジェクト

##オブジェクト

基本はC++と同じですが、_alt-c内で_newしたあと、deleteは不要です。

    var c = new SomeClass

は以下と等価です

    shared_ptr<SomeClass> c(new SomeClass);

##クラス

    class Person {
        //publicがデフォルト

        string name
        int age
        
        //コンストラクタ
        //引数に@を付けると自動で代入してくれます
        this(@name, @age){}
    
        void say(){
            puts 'I'm ' + @name
        }
    }
    
は以下と等価です

    class Person{
    public:
        string name;
        int age;
        
        Person(const string &name_, const int &age_) : name(name_), age(age_){}
        
        void say(){
            puts("I'm " + this->name);
        }
    };



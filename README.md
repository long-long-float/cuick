cight
=====

#メモ

一部C++11の機能を使っている

- 非C++11に切り替えられるようにする？

- 警告を出す

#clightとは

一言でいうと、軽量スクリプト(風)言語です。主に、競技などの比較的小規模なプログラムを想定しています。

#特徴
- C++と100%の互換性

- 簡潔な構文、豊富なシンタックスシュガー

- クロージャ、ブロック

- vector, map, tuple, range, 正規表現リテラル

- 拡張メソッド

- 文字列内での引数展開、ヒアドキュメント

- メタプログラミング（超マクロ）

#字句構造

文の最後の;は不要です

//, /**/はコメントです

#基本の制御構造

##unless

ifの否定

##後置if, unless, for, foreach, while

    ... xxx(expression)

##foreach

    for(var : container){
        ...
    }

C++のSTLのコンテナを反復処理します。
    
    vector<int> ary = [1, 2, 3]
    int sum = 0
    for(e : ary){
        sum += e
    }

は以下と等価です

    vector<int> ary(3);
    ary.push_back(1);
    ary.push_back(2);
    ary.push_back(3);
    auto sum = 0;
    for(vector<int>::iterator e = ary.begin() ; e != ary.end() ; e++){
        sum += * e;
    }

containerにrangeリテラルを指定すると、varをカウンターとしたfor文になります

##foreach(for native array)

    for(var : array:range){
        ...
    }

生配列を反復処理します。rangeを省略すると、0...sizeof(array) / sizeof(array[0])とします

    int[] ary = raw[1, 2, 3]
    for(e : ary:0..1){
        e ** = 2
    }

は以下と等価です

    int ary[] = {1, 2, 3};
    for(int i = 0;i < sizeof(ary) / sizeof(ary[0]);i++){
        ary[i] = std::pow(ary[i], 2.0);
    }

#関数

##呼び出し

関数呼び出しについては、C++と同じです。ただし、関数の後ろのカッコは省略可能です?_←関数ポインタとの区別がつかない_

##定義

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

- 指定子

inline, __ cdeclをはじめとしたC++のものに加えて、

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

すでに同名のメソッドがある場合は上書きされてしまいます

- memo(n, (max))

メモ化できます

    memo(n) int fact(int n){
        n <= 1 ? 1 : n * fact(n);
    }

は以下と等価です

    int fact(int n){
        static std::map<int> fact_memo;
        int idx = n;
        std::map<int>::iterator itr = fact_memo.find(idx);
        if(itr != fact_memo.end()) return * itr;
        return fact_memo[idx] = (n <= 1 ? 1 : n * fact(idx));
    }

最大数maxを指定するとより高速な配列になります。

#クロージャ、ブロック

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
    
    auto twice = Closure();

##ブロック

関数の引数にブロック宣言をすると、関数にブロックを渡すことができます。

関数-define-extendを参照してください

##yield

関数内で、yieldを呼び出すと、引数のブロックに展開されます。吐き出されるコードはインライン展開されたものです。

    extend int.times(){
        for(i in 0...this) yield(i)
    }
    
    puts 'begin'
    3.times{ puts i }

は以下と等価です

    cout << "begin" << endl;
    for(int i = 0;i < 3;i++) cout << i << endl;

#クラス

##クラス

    class Person {
        //publicがデフォルト

        //コンストラクタ
        //引数に@を付けると自動で代入してくれます
        this(@name, @age_){}
    
        void say(){
            puts 'I'm ' + @name
        }
        
        //プロパティ
        prop int age
        get{
            //鯖を読む
            @age_ - 5
        }
        set(val) { @age_ = val }
        
    private:
        string name
        int age_
    }
    
は以下と等価です

    class Person{
    public:
        Person(const string &name_, const int &age_) : name(name_), age(age_){}
        
        void say(){
            puts("I'm " + this->name);
        }
        
        friend class PropName{
        prrivate:
            Person &parent;
        public:
            PropName(Person &parent_) : parent(parent_){}
            operator int() const {
                return parent.age_ - 5;
            }
            const int& operator=(const int& val){
                return parent.age_ = val;
            }
        };
    private:
        string name;
        int age;
    };


#リテラル

##文字列リテラル

""で囲んだ文字列の中#{式}を入れると、コンパイル時に展開されます。

    var a = 10, b = 20
    puts "a + b = #{a + b}"

は以下と等価です

    auto a = 10;
    auto b = 20;
    std::stringstream ss;
    ss << "a + b = " << a + b;
    puts(ss.str());

##array, vector, map, tupleリテラル

- array

    ary(<型>)[要素, ...]

- vector

    (vec)(<型>)[要素, ...] 

- map

    (map)(<keyの型, valの型>)[key : val, ...]

- tuple

    (tuple)(<型, ...>)<要素, ...>

型を省略した場合、1番目の要素をdecltypeします(tupleの場合は全部の要素)

##rangeリテラル

範囲を指定できるところでのみ使うことができます←変数として使えるようにする？

a..bは[a, b]、a...bは[a, b)です

# @命令

C++のプリプロセッサに加えて、

- input

定義を書くだけで、標準入力から読み取ることができます

    int n, a[10000];
    @input{
        n
        a[0...n]
    }
    var sum = 0
    foreach(i in 0...n) sum += i
    print sum

- with(object)

このブロック内のレシーバーを省略できます

    Person p
    @with(p){
        .age = 10
        .name = 'John'
    }

#その他

##vectorのスライス

配列のindexにrangeを使うと、その範囲のvectorが生成されます

    var ary = [1, 3, 4, 10, 12]
    ary[1..3] //=>3, 4, 10

##print, prints

これらはstd::coutに変換されます。printsは、要素ごとにスペースが入ります

    print 1, 2, 3
    prints 1, 2, 3



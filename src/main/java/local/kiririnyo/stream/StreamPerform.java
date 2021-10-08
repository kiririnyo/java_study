package local.kiririnyo.stream;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamPerform {

    private static final List<StreamParamBean> NIJIGAKU_LIST = List.of(
            new StreamParamBean("上原歩夢", 2),
            new StreamParamBean("中須かすみ", 1),
            new StreamParamBean("桜坂しずく", 1),
            new StreamParamBean("朝香果林", 3),
            new StreamParamBean("宮下愛", 2),
            new StreamParamBean("近江彼方", 3),
            new StreamParamBean("優木せつ菜", 2),
            new StreamParamBean("エマ・ヴェルデ", 3),
            new StreamParamBean("天王寺璃奈", 1),
            new StreamParamBean("三船栞子", 1),
            new StreamParamBean("ミア・テイラー", 3),
            new StreamParamBean("鐘 嵐珠", 2)
    );

    private static final List<StreamParamBean> AQOURS_LIST = List.of(
            new StreamParamBean("高海千歌", 2),
            new StreamParamBean("桜内梨子", 2),
            new StreamParamBean("松浦果南", 3),
            new StreamParamBean("黒澤ダイヤ", 3),
            new StreamParamBean("渡辺曜", 2),
            new StreamParamBean("津島善子", 1),
            new StreamParamBean("国木田花丸", 1),
            new StreamParamBean("小原鞠莉", 3),
            new StreamParamBean("黒澤ルビィ", 1)
    );

    public static void main(String[] args) {
        forEach();
        map();
        flatMap();
        filter();
        parallelStream();
    }

    private static void forEach() {
        // リストの要素を見て何か処理をしたいときに使う
        // 返り値はない
        System.out.println("-- forEach --");
        NIJIGAKU_LIST.forEach(idle -> System.out.println(idle.getName()));

        // forEachOrderedは並列処理にした場合も順番を担保する
        System.out.println("-- parallel forEach --");
        NIJIGAKU_LIST.parallelStream().forEach(idle -> System.out.println(idle.getName()));
        System.out.println("-- parallel forEachOrdered --");
        NIJIGAKU_LIST.parallelStream().forEachOrdered(idle -> System.out.println(idle.getName()));
    }

    private static void map() {
        // リストの要素に対して型の変換を行い、新たなstreamを生成する
        final Stream<String> st = NIJIGAKU_LIST.stream().map(StreamParamBean::getName);

        // 生成したstreamに対してcollect等の操作をし、新たな配列や文字列を生成する使い方が多い
        final List<String> nameList = st.collect(Collectors.toList());

        System.out.println("-- map --");
        System.out.println(nameList);
    }

    private static void flatMap() {

        // 型の変換を行う点ではmapと同じだが、こちらはstreamへの変換
        // mapが1:1ならこちらは1:Nの変換となる
        System.out.println("-- flatMap --");

        // 2次元配列が生成されるわけではなくflatになる
        // 例えば2次元配列をflatMapでstreamに変換すると、1次元配列にまとまる
        final List<List<StreamParamBean>> schoolIdleList = List.of(AQOURS_LIST, NIJIGAKU_LIST);
        final Stream<StreamParamBean> st = schoolIdleList.stream().flatMap(Collection::stream);
        final List<String> nameList = st.map(StreamParamBean::getName).collect(Collectors.toList());
        System.out.println(nameList);

        // 1次元配列に他の要素を加えた1次元配列にすることも可能
        final List<Object> mixedList = NIJIGAKU_LIST.stream()
                .flatMap(idle -> Stream.of(idle.getName(), idle.getSchoolYear()))
                .collect(Collectors.toList());
        System.out.println(mixedList);
    }

    private static void filter() {
        // 指定した条件に合うものだけを残したstreamを生成する
        final Stream<StreamParamBean> st = NIJIGAKU_LIST.stream().filter(idle -> idle.getSchoolYear() == 3);

        // 生成したstreamに対してmapを続けたりcollectで新たな配列などを生成する使い方が多い
        final List<String> thirdGradeMemberNameList = st.map(StreamParamBean::getName).collect(Collectors.toList());

        System.out.println("-- filter --");
        System.out.println(thirdGradeMemberNameList);
    }

    private static void parallelStream() {
        // 1~10000000の整数配列
        int[] values = IntStream.rangeClosed(1, 10000000).toArray();
        List<Integer> valueList = Arrays.stream(values).boxed().collect(Collectors.toList());

        // parallelStreamを使うと並列処理にできる
        System.out.println("-- not parallel --");
        long startTime = System.currentTimeMillis();
        System.out.println(valueList.stream().collect(Collectors.summarizingInt(i -> i)));
        System.out.println(System.currentTimeMillis() - startTime);

        System.out.println("-- parallel --");
        startTime = System.currentTimeMillis();
        System.out.println(valueList.parallelStream().collect(Collectors.summarizingInt(i -> i)));
        System.out.println(System.currentTimeMillis() - startTime);
    }
}




package local.kiririnyo.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamPerform {

    private static final List<StreamParamBean> PARAM_LIST = List.of(
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

    public static void main(String[] args) {
        forEach();
        map();
        parallelStream();
    }

    private static void forEach() {
        // リストの要素を見て何か処理をしたいときに使う
        // 返り値はない
        System.out.println("-- forEach --");
        PARAM_LIST.forEach(param -> System.out.println(param.getName()));

        // forEachOrderedは並列処理にした場合も順番を担保する
        System.out.println("-- parallel forEach --");
        PARAM_LIST.parallelStream().forEach(param -> System.out.println(param.getName()));
        System.out.println("-- parallel forEachOrdered --");
        PARAM_LIST.parallelStream().forEachOrdered(param -> System.out.println(param.getName()));
    }

    private static void map() {
        // リストの要素に対してなにかしら処理をし、新たなstreamを生成します
        Stream<String> st = PARAM_LIST.stream().map(StreamParamBean::getName);

        // 生成したstreamに対してcollect等の操作をし、新たな配列や文字列を生成する使い方が多いです
        final List<String> nameList = st.collect(Collectors.toList());

        System.out.println("-- map and collect --");
        System.out.println(nameList);
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




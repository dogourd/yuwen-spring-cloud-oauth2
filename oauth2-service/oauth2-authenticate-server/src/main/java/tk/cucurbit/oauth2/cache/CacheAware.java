package tk.cucurbit.oauth2.cache;

public interface CacheAware<K, V> {

    /**
     * <h2> 添加缓存信息 </h2>
     *
     * @return if k not exists return null
     *         else return old value
     */
    V add(K k, V v);

    /**
     * <h2> 获取缓存信息 </h2>
     * */
    V get(K k);

    /**
     * <h2>  更新缓存信息 </h2>
     *
     * */
    void update(K k, V v);


    /** <h2> 删除缓存k 中的v信息 </h2> */
    void delete(K k, V v);

    /** <h2> 删除缓存信息 </h2> */
    void clear(K k);
}

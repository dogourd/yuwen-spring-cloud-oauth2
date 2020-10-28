
select
    pg_attribute.attnum as "序号",
    pg_namespace.nspname as "模式",
    pg_class.relname as "表名",
    cast(
            obj_description(relfilenode, 'pg_class') as varchar
    )as "表注释",
    pg_attribute.attname as "字段名",
    concat_ws('', pg_type.typname) as "字段类型",
    concat_ws('',SUBSTRING(format_type(
            pg_attribute.atttypid, pg_attribute.atttypmod)
            from '(?<=\()(.*)(?=\))')
    ) as "字段长度",
    pg_description.description as "字段注释"
from pg_class
join pg_attribute on pg_class.oid = pg_attribute.attrelid
join pg_type on pg_attribute.atttypid = pg_type.oid
join pg_description
    on pg_attribute.attrelid = pg_description.objoid
    and pg_attribute.attnum = pg_description.objsubid
join pg_namespace on pg_namespace.oid = pg_class.relnamespace
order by pg_class.relname desc, pg_attribute.attnum;

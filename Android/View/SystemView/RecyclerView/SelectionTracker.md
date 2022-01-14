### 什么是SelectionTracker？
SelectionTracker用于管理RecyclerView实例中的item选择。

一款长按就可以多选的RecycerView 选择器，支持滑动多选。

使用 SelectionTracker.Builder创建SelectionTracker的实例。

######  ItemDetails
实现ItemDetails抽象类 为选择库提供对特定RecyclerView item信息的访问。
该类是在特定活动上下文中控制选择库行为的关键组件。主要用于绑定ViewHolder时，获取某个item的详情。为DetailsLookup接口服务，通过DetailsLookup中的getItemDetails()方法，返回点击处Item的信息。

- getPosition()
      返回当前条目索引，由onBindViewHolder()传入
- getSelectionKey()
      返回当前条目的key，默认为position
- inSelectionHotspot(e: MotionEvent)
  itemView 中通常包含与复选框类似的区域，例如电子邮件左侧的图标。“选择热点”提供了一种识别这些区域的机制，库可以直接将这些区域中的点击转换为选择状态的变化。
  返回：
  如果事件位于应直接解释为用户希望选择项目的项目区域中，则为true。这对于复选框和其他侧重于启用选择的UI功能非常有用。

- inDragRegion(e: MotionEvent)
      当前区域，是否为可推拽区域中，在RecyclerView开发过程中，会有一些特殊的可展开的Item View，在展开时，当MotionEvent发生在这样的区域中时，您应该返回并将其标识为不在拖动区域中。

###### DetailsLookup
选择库在需要访问有关区域和/或ItemDetailsLookup的信息时调用getItemDetails（MotionEvent）。MotionEvent下的ItemDetails。您的实现必须与相应的RecyclerView实例协商ViewHolder查找，并将ViewHolder实例的后续转换为ItemDetailsLookup。ItemDetails实例。
详细记录接口,

- getItemDetails(e: MotionEvent) : ItemDetails<Long?>?
  获取点击区域Item详情，只会传递点击事件，需要配合RecyclerView.findChildViewUnder() 来获取View对象，



###### StorageStrategy
保存状态下存储选中Key的策略。

系统默认提供一个实例的方法，StorageStrategy.createLongStorage()

######  ItemKeyProvider
通过RecyclerView的实力，提供一个selection library稳定 访问的 selection keys 标识，默认就是索引
- 构造ItemKeyProvider(@Scope int scope)
  - SCOPE_MAPPED
  提供对所有数据的访问，无论数据是否绑定到视图

  - SCOPE_CACHED
  为视图中最近绑定的项提供对缓存数据的访问。

-  K getKey(int position)

-  int getPosition(K key);



######  SelectionTracker
通过Builder创建selectionTracker的实例，主要参数
```Java
public Builder(
            //选择器的唯一标识
            @NonNull String selectionId,
            //绑定的recyclerView对象
            @NonNull RecyclerView recyclerView,
            //  ItemKeyProvider实现类
            @NonNull ItemKeyProvider<K> keyProvider,
            //  DetailsLookup实现类
            @NonNull ItemDetailsLookup<K> detailsLookup,
            // StorageStrategy.createLongStorage()
            @NonNull StorageStrategy<K> storage) {
        //...
    }
```

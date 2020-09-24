### 单例模式

#### 应用场景

音乐播放器  回收站  打印机。。。。

#### 实现方式

通过静态方法--new--(cls,*a,**b)返回相同的内存地址  就可以保证单例 

    class MusicPlayer(object):
    instance = None #类属性  init中为实例属性

    def __new__(cls,*args,**kargs):
        if cls.instance is None:
            cls.instance = super().__new__(cls)
        return cls.instance
    





o1 = MusicPlayer()
print(o1)

o2 = MusicPlayer()
print(o2)

#### 只执行一次初始化
python类在实例化的一个对象的时候 每次都会调用new init方法  通过设置一个类属性flag标记  让类new和init方法只被调用一次
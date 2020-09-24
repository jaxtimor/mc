class MusicPlayer(object):
    instance = None

    def __new__(cls,*args,**kargs):
        if cls.instance is None:
            cls.instance = super().__new__(cls)
        return cls.instance
    





o1 = MusicPlayer()
print(o1)

o2 = MusicPlayer()
print(o2)
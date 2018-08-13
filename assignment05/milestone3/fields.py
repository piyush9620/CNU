from builtins import object, isinstance, str, TypeError


class Field:
    def __init__(self, init_val = None):
        self._val = init_val

    def __set__(self, instance, value):
        self._val = value

    def __get__(self, instance, value):
        return self._val


class Fields:
    class CharField(Field):
        def __set__(self, instance, value):
            if not isinstance(value, str):
                raise TypeError(instance, str, value)
            super().__set__(instance, value)

    class IntegerField(Field):
        def __set__(self, instance, value):
            if not isinstance(value, int):
                raise TypeError(instance, int, value)
            super().__set__(instance, value)

    class FloatField(Field):
        def __set__(self, instance, value):
            if not isinstance(value, float):
                raise TypeError(instance, float, value)
            super().__set__(instance, value)

    class BooleanField(Field):
        def __set__(self, instance, value):
            if not isinstance(value, bool):
                raise TypeError(instance, bool, value)
            super().__set__(instance, value)

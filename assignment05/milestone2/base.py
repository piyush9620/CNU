from fields import Field, Fields


class Base(object):
    id = Fields.IntegerField()

    def __new__(cls, *args, **kwargs):
        for i, j in kwargs.items():
            setattr(cls, i, j)

        return super(Base, cls).__new__(cls)

    def __init_subclass__(cls, **kwargs):
        data = []
        cls.id = Fields.IntegerField()
        for attr_name, attr_val in cls.__dict__.items():
            if isinstance(attr_val, Field):
                setattr(cls, attr_name, attr_val)
                data.append(attr_name)

        cls._qkeys = ",".join(["`{}`".format(k) for k in data])
        cls._table_name = cls.__name__.lower()

        def _generate_findBy_function(param):
            def func(value):
                query = "SELECT {} FROM {} WHERE {} = \"{}\""\
                    .format(cls._qkeys, cls._table_name, param, value)
                print(query)
                #     Run query
                #     create_obj_from_result

            func.__name__ = "findBy{}".format(param.title())
            setattr(cls, func.__name__, func)

        def _generate_findByOr_function(param1, param2):
            def func(value1, value2):
                query = "SELECT {} FROM {} WHERE {} = \"{}\" OR {} = \"{}\""\
                    .format(cls._qkeys, cls._table_name, param1, value1, param2, value2)
                print(query)
                #     Run query
                #     create_obj_from_result

            func.__name__ = "findBy{}Or{}".format(param1.title(), param2.title())
            setattr(cls, func.__name__, func)

        def _generate_findByAnd_function(param1, param2):
            def func(value1, value2):
                query = "SELECT {} FROM {} WHERE {} = \"{}\" AND {} = \"{}\""\
                    .format(cls._qkeys, cls._table_name, param1, value1, param2, value2)
                print(query)
                #     Run query
                #     create_obj_from_result

            func.__name__ = "findBy{}And{}".format(param1.title(), param2.title())
            setattr(cls, func.__name__, func)

        def _generate_all_findBy_function():
            for i in data:
                for j in data:
                    if i == j:
                        _generate_findBy_function(i)
                    else:
                        _generate_findByOr_function(i, j)
                        _generate_findByOr_function(j, i)
                        _generate_findByAnd_function(i, j)
                        _generate_findByAnd_function(j, i)

        def _generate_get_function():
            def get(value):
                query = "SELECT {} FROM {} WHERE {} = {}"\
                    .format(cls._qkeys, cls._table_name, "id", value)
                print(query)
            setattr(cls, get.__name__, get)

        def _generate_all_function():
            def all():
                query = "SELECT {} FROM {}"\
                    .format(cls._qkeys, cls._table_name)
                print(query)
            setattr(cls, all.__name__, all)

        # def _generate_save_function():

        def _generate_save_function():
            def save(self):
                ks = []
                vs = []
                for k in data:
                    if getattr(self, k) is not None:
                        ks.append(k)
                        vs.append(getattr(self, k))
                query = "INSERT INTO {} ({}) VALUES ({})"\
                    .format(cls._table_name, ",".join(["`{}`".format(k) for k in ks]), ",".join(["\"{}\"".format(v) for v in vs]))
                print(query)

            setattr(cls, save.__name__, save)

        _generate_all_findBy_function()
        _generate_get_function()
        _generate_all_function()
        _generate_save_function()

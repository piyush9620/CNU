from fields import Field, Fields
import re


class Model(type):
    def __getattr__(cls, name):
        pattern = re.compile("findBy(.*)")
        result = pattern.search(name)
        if result:
            joinedParams = result.group(1)
            splittedParams = [a.split("And") for a in joinedParams.split("Or")]
            allParams = [x for a in splittedParams for x in a]
            joinedAgainParams = " OR ".join(
                [
                    " AND ".join(
                        ["`{}` = \"{{}}\"".format(b.lower()) for b in x]
                    )
                    for x in splittedParams
                ]
            )

            def x(*args, **kwargs):
                if len(args) != len(allParams):
                    raise NotImplementedError("INVALID NUMBER OF ARGUMENTS")
                query = "SELECT {} FROM `{}` WHERE {}".format(cls._qkeys, cls._table_name, joinedAgainParams)
                print(query.format(*args))

            return x


class Base(metaclass=Model):
    id = Fields.IntegerField()

    def __new__(cls, *args, **kwargs):
        self = super(Base, cls).__new__(cls)
        for i, j in kwargs.items():
            setattr(self, i, j)

        return self

    def __init_subclass__(cls, **kwargs):
        data = []
        setattr(cls, "id", Fields.IntegerField())
        for attr_name, attr_val in cls.__dict__.items():
            if isinstance(attr_val, Field):
                setattr(cls, attr_name, attr_val)
                data.append(attr_name)

        cls._fields = data
        cls._table_name = cls.__name__.lower()
        cls._qkeys = ",".join(["`{}`".format(k) for k in data])

    def save(self):
        ks = []
        vs = []
        for k in self._fields:
            if getattr(self, k) is not None:
                ks.append(k)
                vs.append(getattr(self, k))
        query = "INSERT INTO `{}` ({}) VALUES ({})" \
            .format(self._table_name, ",".join(["`{}`".format(k) for k in ks]),
                    ",".join(["\"{}\"".format(v) for v in vs]))
        print(query)

    @classmethod
    def get(cls, value):
        query = "SELECT {} FROM `{}` WHERE `{}` = {}".format(
            cls._qkeys, cls._table_name, "id", value
        )
        print(query)

    @classmethod
    def all(cls):
        query = "SELECT {} FROM `{}`".format(
            cls._qkeys, cls._table_name
        )
        print(query)

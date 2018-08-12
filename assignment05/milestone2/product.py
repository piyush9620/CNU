from base import Base
from fields import Fields


class Product(Base):
    name = Fields.CharField()
    sell_price = Fields.FloatField()
    buy_price = Fields.FloatField()
    quantity = Fields.IntegerField()
    active = Fields.BooleanField()


p = Product(name="a")
pass

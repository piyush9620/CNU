from base import Base
from fields import Fields


class Product(Base):
    name = Fields.CharField()
    sell_price = Fields.FloatField()
    buy_price = Fields.FloatField()
    quantity = Fields.IntegerField()
    active = Fields.BooleanField()


p = Product(name="a")
Product.findByNameOrIdAndBOrC("A", "b", 1, "d")
print("A","b", "c")
p.save()
Product.all()
Product.get(1)

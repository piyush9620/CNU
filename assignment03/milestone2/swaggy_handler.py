from db import conn

INSERT_LOCATION_QUERY = (
    "INSERT INTO locations "
    "(locality, district, state, country, pincode) "
    "VALUES (%s, %s, %s, %s, %s)"
)

SELECT_LOCATION_QUERY = (
    "SELECT id FROM locations "
    "WHERE pincode = %s AND locality = %s AND district = %s AND state = %s AND country = %s"
)

INSERT_ADDRESS_QUERY = (
    "INSERT INTO address "
    "(address, location_id, latitude, longitude) "
    "VALUES (%s, %s, %s, %s)"
)

INSERT_RESTAURANT_QUERY = (
    "INSERT INTO restaurants "
    "(`name`, address_id, rating, review_count) "
    "VALUES (%s, %s, %s, %s)"
)

SELECT_ITEM_TYPE_QUERY = (
    "SELECT id FROM item_types "
    "WHERE `type` = %s"
)

INSERT_ITEM_TYPE_QUERY = (
    "INSERT INTO item_types "
    "(`type`) VALUES (%s)"
)

def createLocation(locality, district, state, country, pincode):
    cursor = conn.cursor()
    data = (locality, district, state, country, pincode)
    cursor.execute(INSERT_LOCATION_QUERY, data)
    conn.commit()
    location_id = cursor.lastrowid
    cursor.close()
    return location_id

def getLocation(locality, district, state, country, pincode):
    cursor = conn.cursor()
    data = (pincode, locality, district, state, country)
    cursor.execute(SELECT_LOCATION_QUERY, data)
    
    location_id = cursor.fetchone()
    if location_id:
        location_id = location_id[0]
    return location_id

def createOrGetLocation(locality, district, state, country, pincode):
    location_id = getLocation(locality, district, state, country, pincode)
    if location_id is None:
        location_id = createLocation(locality, district, state, country, pincode)

    return location_id
    
def createAddress(address, location_id, latitude, longitude):
    cursor = conn.cursor()
    data = (address, location_id, latitude, longitude)
    cursor.execute(INSERT_ADDRESS_QUERY, data)
    conn.commit()
    address_id = cursor.lastrowid
    cursor.close()
    return address_id

def createRestaurant(name, address_id, rating, review_count):
    cursor = conn.cursor()
    data = (name, address_id, rating, review_count)
    cursor.execute(INSERT_RESTAURANT_QUERY, data)
    conn.commit()
    restaurant_id = cursor.lastrowid
    cursor.close()
    return restaurant_id

def getItemType(item_type):
    cursor = conn.cursor()
    cursor.execute(SELECT_ITEM_TYPE_QUERY, (item_type, ))
    item_type_id = cursor.fetchone()
    if not item_type_id:
        return False
    else:
        return True

def createItemType(item_type):
    cursor = conn.cursor()
    cursor.execute(INSERT_ITEM_TYPE_QUERY, (item_type, ))
    conn.commit()
    cursor.close()


def createItemTypes(item_types):
    for item_type in item_types:
        if not getItemType(item_type):
            createItemType(item_type)
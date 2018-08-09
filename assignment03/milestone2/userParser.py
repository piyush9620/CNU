import json
from swaggy_handler import createUser


def main():
    users = open('dataset/user.json')
    user_mapping = {}

    for user in users.readlines():
        if not user:
            continue

        user = json.loads(user)
        username = user['user_id']
        fullname = user['name']

        user_id = createUser(username, fullname)
        user_mapping[user['user_id']] = user_id

    with open('user_mapping.json', 'w') as f:
        json.dump(user_mapping, f)


if __name__ == '__main__':
    main()

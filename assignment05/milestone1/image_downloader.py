import threading
import requests

thread_lock = threading.Lock()

class Logger(object):
    def __init__(self, f):
        self.f = f

    def __call__(self, *args):
        print("[*] Downloading Image: ", args[0])
        self.f(self, *args)
        print("[+] Downloaded Image: ", args[0])


class ImageDownloader(threading.Thread):
    def __init__(self, thread_id, images):
        threading.Thread.__init__(self)
        self.__thread_id = thread_id
        self.__images = images

    @Logger
    def downloadImage(self, image):
        pass
        with open("images/" + image.split('/')[-1], 'wb') as f:
            f.write(requests.get(image).content)

    def run(self):
        while True:
            thread_lock.acquire()

            try:
                image = next(self.__images)
                if not image:
                    thread_lock.release()
                    break
            except StopIteration:
                thread_lock.release()
                break

            thread_lock.release()
            self.downloadImage(image.strip())


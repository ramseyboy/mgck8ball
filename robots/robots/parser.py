import os.path


class UserAgent:
    def __init__(self, user_agent, disallows):
        self.user_agent = user_agent
        self.disallows = disallows

        # def __str__(self):
        #     return "foo"


class Robot:
    def __init__(self, user_agents, site_maps):
        self.user_agents = user_agents
        self.site_maps = site_maps

    def __str__(self):
        return "%s, %s" % (self.user_agents, self.site_maps)

    __repr__ = __str__


class Parser:
    __USER_AGENT = "user-agent"
    __ALLOW = "allow"
    __DISALLOW = "disallow"
    __CRAWL_DELAY = "crawl-delay"
    __REQUEST_RATE = "request-rate"
    __ROBOT_VERSION = "robot-version"
    __VISIT_TIME = "visit-time"
    __SITEMAP = "sitemap"

    def __init__(self, input_file):
        self.input_seq = self.__filter(input_file)

        self.directives = [self.__USER_AGENT,
                           self.__ALLOW,
                           self.__DISALLOW,
                           self.__CRAWL_DELAY,
                           self.__REQUEST_RATE,
                           self.__ROBOT_VERSION,
                           self.__VISIT_TIME,
                           self.__SITEMAP]

    @staticmethod
    def __filter(input_file):
        """
        Generator function that returns a filtered iterator

        :param input_file: the robots.txt input file
        :return: a generator that has had new lines filtered out
        """
        for line in input_file.readlines():
            if (line != '\n') and (not line.startswith('#')):
                yield line.strip()

    @staticmethod
    def __split(line):
        """
        :param line: line in text file
        :return: a key value mapping for the input line
        """
        return line.lower().split(":", 1)

    def parse(self):

        user_agents = {}
        site_maps = []

        cur_user_agent = None
        cur_user_agent_disallows = []
        for line in self.input_seq:
            (key, val) = self.__split(line)

            if key == self.__USER_AGENT:
                cur_user_agent = val
                if cur_user_agent is not None:
                    user_agents[cur_user_agent] = cur_user_agent_disallows
                    cur_user_agent_disallows.clear()

            elif key == self.__DISALLOW:
                cur_user_agent_disallows.append(val)

            elif key == self.__SITEMAP:
                site_maps.append(val)

        return Robot(user_agents, site_maps)


if __name__ == '__main__':
    resource_path = os.path.join(os.path.split(__file__)[0], "resources")
    robots_path = os.path.join(resource_path, "usa-robots.txt")

    robots = open(robots_path, 'r')

    robot = Parser(robots).parse()
    print(robot)

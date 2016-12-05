import os.path


class Robot:
    def __init__(self, user_agents, site_maps):
        self.user_agents = user_agents
        self.site_maps = site_maps

    def __str__(self):
        return "%s, %s" % (self.user_agents, self.site_maps)

    __repr__ = __str__


class _ParserState:
    def __init__(self, state, val):
        self.state = state
        self.val = val


class _ParserStateMachine:
    # States
    __USER_AGENT = "user-agent"
    __ALLOW = "allow"
    __DISALLOW = "disallow"
    __CRAWL_DELAY = "crawl-delay"
    __REQUEST_RATE = "request-rate"
    __ROBOT_VERSION = "robot-version"
    __VISIT_TIME = "visit-time"
    __SITEMAP = "sitemap"

    def __init__(self):
        self.cur_state = None
        self.directives = [self.__USER_AGENT,
                           self.__ALLOW,
                           self.__DISALLOW,
                           self.__CRAWL_DELAY,
                           self.__REQUEST_RATE,
                           self.__ROBOT_VERSION,
                           self.__VISIT_TIME,
                           self.__SITEMAP]

        self.user_agents = {}
        self.site_maps = []

        self.cur_user_agent = None
        self.cur_user_agent_disallows = []

    def current_state(self):
        return self.cur_state

    def next_state(self, state: _ParserState):
        self.cur_state = state

        if state.state == self.__USER_AGENT:
            cur_user_agent = state.val
            if cur_user_agent is not None:
                self.user_agents[cur_user_agent] = self.cur_user_agent_disallows
                self.cur_user_agent_disallows.clear()

        elif state.state == self.__DISALLOW:
            self.cur_user_agent_disallows.append(state.val)

        elif state.state == self.__SITEMAP:
            self.site_maps.append(state.val)

    def robot(self):
        return Robot(self.user_agents, self.site_maps)


class Parser:
    def __init__(self, input_file):
        self.input_seq = self.__filter(input_file)
        self.state_machine = _ParserStateMachine()

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
        for line in self.input_seq:
            (state, val) = self.__split(line)
            self.state_machine.next_state(_ParserState(state, val))

        return self.state_machine.robot()


if __name__ == '__main__':
    resource_path = os.path.join(os.path.split(__file__)[0], "resources")
    robots_path = os.path.join(resource_path, "usa-robots.txt")

    robots = open(robots_path, 'r')
    robot = Parser(robots).parse()
    print(robot)

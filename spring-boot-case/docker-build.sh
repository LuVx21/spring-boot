#!/bin/zsh

(( ${+commands[docker-compose]} )) && dco='docker-compose' || dco='docker compose'

mvnd clean package -Dmaven.test.skip=true \
&& ($dco down && $dco up --build -d) \
&& mvnd clean

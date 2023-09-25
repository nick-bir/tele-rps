dev-gui: deps-gui
	cd tele-rps-gui && yarn serve

test-gui:
	cd tele-rps-gui && yarn test:unit --watch

deps-gui:
	cd tele-rps-gui && yarn install


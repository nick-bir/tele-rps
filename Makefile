dev-gui: deps-gui
	cd tele-rps-gui && yarn dev

test-gui:
	cd tele-rps-gui && yarn test:unit

deps-gui:
	cd tele-rps-gui && yarn install


import { shallowMount } from '@vue/test-utils';
import App from './App.vue';
import GameInstructions from './ui/GameInstructions.vue';

describe('GuiApp.vue', () => {
    describe('When no game started', () => {
        it('- display instructions', () => {
            const { isInstructionsVisible } = setup({ gameStarted: false });

            expect(isInstructionsVisible()).toBe(true);
        });
    });
});

function setup({ gameStarted = false }) {
    const wrapper = shallowMount(App);

    return {
        wrapper,
        isInstructionsVisible: () =>
            wrapper.findComponent(GameInstructions).isVisible(),
    };
}

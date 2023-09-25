import { shallowMount } from '@vue/test-utils';
import GuiApp from './GuiApp.vue';
import GameInstructions from './components/GameInstructions.vue';

describe('GuiApp.vue', () => {
    describe('When no game started', () => {
        it('- display instructions', () => {
            const { isInstructionsVisible } = setup({ gameStarted: false });

            expect(isInstructionsVisible()).toBe(true);
        });
    });
});

function setup({ gameStarted = false }) {
    const wrapper = shallowMount(GuiApp);

    return {
        wrapper,
        isInstructionsVisible: () =>
            wrapper.findComponent(GameInstructions).isVisible(),
    };
}

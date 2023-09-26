import { describe, it, vi, expect } from 'vitest';
import { shallowMount } from '@vue/test-utils';
import App from './App.vue';
import Instructions from './ui/Instructions.vue';
import Game from './ui/Game.vue';

import * as Api from './logic/api';
const useApiSpy = vi.spyOn(Api, 'useApi');

describe('GuiApp.vue', () => {
    describe('when no game started', () => {
        it('- display instructions', () => {
            const { isInstructionsVisible } = setup({ isGameStarted: false });
            expect(isInstructionsVisible()).toBe(true);
        });
    });

    describe('when game is started', () => {
        it.only('- show game ui', () => {
            const { isGameUiVisible } = setup({ isGameStarted: true });
            expect(isGameUiVisible()).toBe(true);
        });
    });
});

function setup({ isGameStarted = false }) {
    if (isGameStarted) {
        useApiSpy.mockReturnValue({
            isGameStarted: true,
        });
    }

    const wrapper = shallowMount(App);

    const isInstructionsVisible = () =>
        wrapper.findComponent(Instructions).exists();
    const isGameUiVisible = () => wrapper.findComponent(Game).exists();

    return {
        wrapper,
        isInstructionsVisible,
        isGameUiVisible,
    };
}

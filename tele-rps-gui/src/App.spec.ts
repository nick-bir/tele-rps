import { describe, it, expect } from 'vitest';
import { shallowMount } from '@vue/test-utils';
import App from './App.vue';
import Instructions from './ui/Instructions.vue';
import Game from './ui/Game.vue';
import GameResults from './ui/GameResults.vue';

import { useApi } from './logic/api';

describe('Main screen (App.vue)', () => {
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

    describe('when game is finished', () => {
        it.only('- show game results', () => {
            const { isGameResultsVisible } = setup({ isGameFinished: true });
            expect(isGameResultsVisible()).toBe(true);
        });
    });
});

function setup({ isGameStarted = false, isGameFinished = false }) {
    const { setGameStatus } = useApi();

    if (isGameStarted) {
        setGameStatus('started');
    }
    if (isGameFinished) {
        setGameStatus('finished');
    }

    const wrapper = shallowMount(App);

    const isInstructionsVisible = () =>
        wrapper.findComponent(Instructions).exists();
    const isGameUiVisible = () => wrapper.findComponent(Game).exists();
    const isGameResultsVisible = () =>
        wrapper.findComponent(GameResults).exists();

    return {
        wrapper,
        isInstructionsVisible,
        isGameUiVisible,
        isGameResultsVisible,
    };
}

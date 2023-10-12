import { describe, it, expect } from 'vitest';
import { shallowMount } from '@vue/test-utils';
import ResultsPageVue from './ResultsPage.vue';
import HandsVue from '../Hands/Hands.vue';
import { useState } from '../../state';

describe('Results page', () => {
    it('- show hands', () => {
        const { isHandsVisible } = setup();
        expect(isHandsVisible).toBe(true);
    });

    describe('if I won', () => {
        it('- show "You won"', () => {
            const { isYouWonMessageVisible } = setup({ iWon: true });
            expect(isYouWonMessageVisible).toBe(true);
        });
    });

    describe('if I lost', () => {
        it('- show "You lost"', () => {
            const { isYouLostMessageVisible } = setup({ iLost: true });
            expect(isYouLostMessageVisible).toBe(true);
        });
    });
});

function setup({ iWon = false, iLost = false } = {}) {
    const { setGameResult, setGameStatus } = useState();
    setGameStatus('FINISHED');

    if (iWon) {
        setGameResult('VICTORY');
    }

    if (iLost) {
        setGameResult('DEFEAT');
    }

    const wrapper = shallowMount(ResultsPageVue);
    const isHandsVisible = wrapper.findComponent(HandsVue).exists();
    const isYouWonMessageVisible = wrapper.find('.result__win').exists();
    const isYouLostMessageVisible = wrapper.find('.result__lose').exists();

    return {
        isHandsVisible,
        isYouWonMessageVisible,
        isYouLostMessageVisible,
    };
}

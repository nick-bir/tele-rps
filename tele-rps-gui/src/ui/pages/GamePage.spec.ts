import { shallowMount } from '@vue/test-utils';
import { describe, it, expect } from 'vitest';
import Game from './GamePage.vue';
import RpsSelector from '../components/RpsSelector.vue';
import Hands from '../components/Hands/Hands.vue';
import Score from '../components/Score.vue';
import { Figures, useApi } from '../../logic/api';

describe('Game screen (Game.vue)', () => {
    describe('always', () => {
        it('- show score', async () => {
            const { isScoreVisible } = setup();
            expect(isScoreVisible()).toBe(true);
        });
    });
    describe('when new round started', () => {
        it('- hide players hands', () => {
            const { areHandsVisible } = setup();
            expect(areHandsVisible()).toBe(false);
        });
        it('- show rock/paper/scissors selector', () => {
            const { isRpsSelectorVisible } = setup();
            expect(isRpsSelectorVisible()).toBe(true);
        });

        describe('when I made choise', () => {
            it('- hide rock/paper/scissors selector', () => {
                const { isRpsSelectorVisible } = setup({
                    myChoise: 'rock',
                });
                expect(isRpsSelectorVisible()).toBe(false);
            });
            it('- show players hands', async () => {
                const { selectWeapon, areHandsVisible } = setup({});
                await selectWeapon();
                expect(areHandsVisible()).toBe(true);
            });
        });
    });
    describe('when round finished', () => {
        it('- show players hands', () => {
            const { areHandsVisible } = setup({
                myChoise: 'rock',
                opponentChoise: 'paper',
            });
            expect(areHandsVisible()).toBe(true);
        });
    });
});

type SetupProps = {
    myChoise?: Figures;
    opponentChoise?: Figures;
};

function setup({
    myChoise = 'choosing',
    opponentChoise = 'choosing',
}: SetupProps = {}) {
    const { startNewGame, makeMyChoise, makeOpponentChoise } = useApi();

    startNewGame();
    makeMyChoise(myChoise);
    makeOpponentChoise(opponentChoise);

    const wrapper = shallowMount(Game);

    const isScoreVisible = () => wrapper.findComponent(Score).exists();
    const isRpsSelectorVisible = () =>
        wrapper.findComponent(RpsSelector).exists();
    const areHandsVisible = () => wrapper.findComponent(Hands).exists();

    const selectWeapon = () => {
        wrapper.findComponent(RpsSelector).vm.$emit('choise', 'rock');
    };

    return {
        wrapper,
        isScoreVisible,
        isRpsSelectorVisible,
        areHandsVisible,

        selectWeapon,
    };
}

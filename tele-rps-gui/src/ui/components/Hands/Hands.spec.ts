import { shallowMount } from '@vue/test-utils';
import { describe, it, expect } from 'vitest';
import Hands from './Hands.vue';
import OpponentHand from './OpponentHand.vue';
import { Figures, useApi } from '../../../logic/api';
import MyHand from './MyHand.vue';

describe('Players hands (Hands.vue)', () => {
    describe('when I made choise', () => {
        describe('show my hand', () => {
            it('- with choosen weapon', () => {
                const { getMyHandChoise } = setup({ myChoise: 'paper' });
            });
        });
        describe('when opponent', () => {
            describe('has not yet choosen', () => {
                it('- show "choosing" opponent hand', () => {
                    const { getOpponentHandChoise } = setup({
                        opponentChoise: 'choosing',
                    });
                    expect(getOpponentHandChoise()).toBe('choosing');
                });
            });

            describe('has made choise', () => {
                it.only('- show chosen weapon', () => {
                    const { getOpponentHandChoise } = setup({
                        opponentChoise: 'paper',
                    });
                    expect(getOpponentHandChoise()).toBe('paper');
                });
            });
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
    const { makeMyChoise, makeOpponentChoise, startNewGame } = useApi();

    startNewGame();
    makeMyChoise(myChoise);
    makeOpponentChoise(opponentChoise);

    const wrapper = shallowMount(Hands);

    const getMyHandChoise = () => wrapper.findComponent(MyHand).props('choise');
    const getOpponentHandChoise = () =>
        wrapper.findComponent(OpponentHand).props('choise');

    return {
        wrapper,
        getMyHandChoise,
        getOpponentHandChoise,
    };
}

<template>
    <Hand :choise="myWeapon" name="My hand" />
</template>

<script setup lang="ts">
    import { Figures, useState } from '../../state';
    import Hand from './Hand.vue';

    defineProps<{ choise: Figures }>();

    const { myWeapon } = useState();
</script>
../../../state/api

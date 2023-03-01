import { ROOT_ID } from './constants';
import { getBordaCounts, getCountsOfFirstPlaceVotes, getCountsOfVotes } from './transformers';


window.addEventListener('load', () => {
    const root = document.createElement('div');
    root.id = ROOT_ID;
    root.innerText = 'Hey3';
    document.body.appendChild(root);

    console.log(getCountsOfFirstPlaceVotes());
    console.log(getCountsOfVotes());
    console.log(getBordaCounts());
});
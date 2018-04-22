import { AppRegistry } from 'react-native';
import App from './App';


// TODO: remove the repl when release
import './js/shadow.cljs.devtools.client.react_native';

import { main } from './js/darongmean.funan';


AppRegistry.registerComponent('FunanMobile', () => App);

main();


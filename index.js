import { AppRegistry } from 'react-native';
import App from './App';
import WidgetTask from './widgetTask';

AppRegistry.registerComponent('androidWidgetPoc', () => App);
AppRegistry.registerHeadlessTask('WidgetTask', () => WidgetTask);


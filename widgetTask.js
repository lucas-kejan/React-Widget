/**
* @flow
*/

import { NativeModules, ToastAndroid } from 'react-native'
import bgTimer from 'react-native-background-timer'

const { BackgroundTaskBridge } = NativeModules

const charms = [
  {
    id: 'uuid1',
    name: 'First',
    cover: 'goodmorning',
  },
  {
    id: 'uuid2',
    name: 'Second',
    cover: 'night',
  }
]

type TaskInfo = {
  id: string,
}

export default async function widgetTask (taskData: TaskInfo) {
  const {id} = taskData || {}
  bgTimer.setTimeout(() => {
    fetch('https://www.simplifiedcoding.net/demos/marvel/')
    .then((response) => response.json())
    .then((responseJson) => {
      synchronizeWidget(responseJson)
    })
    .catch((error) => {
      console.error(error);
    });
    //synchronizeWidget()
    //triggerCharm(id)
  }, 0)
}

export function synchronizeWidget (a) {
  ToastAndroid.show(`Initializing ...`, ToastAndroid.SHORT);
  //BackgroundTaskBridge.pinWidgetToHomeScreen();
  BackgroundTaskBridge.initializeWidgetBridge(a)
}

function triggerCharm (id) {
  if (!id) return
  ToastAndroid.show(`Triggering ${id}...`, ToastAndroid.SHORT);
}

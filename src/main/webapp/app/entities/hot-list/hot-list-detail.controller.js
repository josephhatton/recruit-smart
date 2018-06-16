(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HotListDetailController', HotListDetailController);

  HotListDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'HotList'];

  function HotListDetailController($scope, $rootScope, $stateParams, previousState, entity, HotList) {
    var vm = this;

    vm.hotList = entity;
    vm.previousState = previousState.name;

    var unsubscribe = $rootScope.$on('recruitsmartApp:hotListUpdate', function (event, result) {
      vm.hotList = result;
    });
    $scope.$on('$destroy', unsubscribe);
  }
})();

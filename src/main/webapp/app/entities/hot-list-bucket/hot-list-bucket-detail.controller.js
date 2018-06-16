(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HotListBucketDetailController', HotListBucketDetailController);

  HotListBucketDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'HotListBucket'];

  function HotListBucketDetailController($scope, $rootScope, $stateParams, previousState, entity, HotListBucket) {
    var vm = this;

    vm.hotListBucket = entity;
    vm.previousState = previousState.name;

    var unsubscribe = $rootScope.$on('recruitsmartApp:hotListBucketUpdate', function (event, result) {
      vm.hotListBucket = result;
    });
    $scope.$on('$destroy', unsubscribe);
  }
})();

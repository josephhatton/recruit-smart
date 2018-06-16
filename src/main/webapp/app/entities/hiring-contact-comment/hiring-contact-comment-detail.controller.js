(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HiringContactCommentDetailController', HiringContactCommentDetailController);

  HiringContactCommentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'HiringContactComment'];

  function HiringContactCommentDetailController($scope, $rootScope, $stateParams, previousState, entity, HiringContactComment) {
    var vm = this;

    vm.hiringContactComment = entity;
    vm.previousState = previousState.name;

    var unsubscribe = $rootScope.$on('recruitsmartApp:hiringContactCommentUpdate', function (event, result) {
      vm.hiringContactComment = result;
    });
    $scope.$on('$destroy', unsubscribe);
  }
})();

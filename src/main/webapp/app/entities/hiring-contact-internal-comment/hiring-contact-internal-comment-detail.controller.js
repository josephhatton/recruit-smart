(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('HiringContactInternalCommentDetailController', HiringContactInternalCommentDetailController);

  HiringContactInternalCommentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'HiringContactInternalComment'];

  function HiringContactInternalCommentDetailController($scope, $rootScope, $stateParams, previousState, entity, HiringContactInternalComment) {
    var vm = this;

    vm.hiringContactInternalComment = entity;
    vm.previousState = previousState.name;

    var unsubscribe = $rootScope.$on('recruitsmartApp:hiringContactInternalCommentUpdate', function (event, result) {
      vm.hiringContactInternalComment = result;
    });
    $scope.$on('$destroy', unsubscribe);
  }
})();

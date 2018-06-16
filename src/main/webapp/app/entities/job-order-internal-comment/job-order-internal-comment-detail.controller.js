(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('JobOrderInternalCommentDetailController', JobOrderInternalCommentDetailController);

  JobOrderInternalCommentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'JobOrderInternalComment'];

  function JobOrderInternalCommentDetailController($scope, $rootScope, $stateParams, previousState, entity, JobOrderInternalComment) {
    var vm = this;

    vm.jobOrderInternalComment = entity;
    vm.previousState = previousState.name;

    var unsubscribe = $rootScope.$on('recruitsmartApp:jobOrderInternalCommentUpdate', function (event, result) {
      vm.jobOrderInternalComment = result;
    });
    $scope.$on('$destroy', unsubscribe);
  }
})();

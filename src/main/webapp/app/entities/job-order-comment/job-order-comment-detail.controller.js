(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('JobOrderCommentDetailController', JobOrderCommentDetailController);

  JobOrderCommentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'JobOrderComment'];

  function JobOrderCommentDetailController($scope, $rootScope, $stateParams, previousState, entity, JobOrderComment) {
    var vm = this;

    vm.jobOrderComment = entity;
    vm.previousState = previousState.name;

    var unsubscribe = $rootScope.$on('recruitsmartApp:jobOrderCommentUpdate', function (event, result) {
      vm.jobOrderComment = result;
    });
    $scope.$on('$destroy', unsubscribe);
  }
})();

(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .controller('CompanyInternalCommentDetailController', CompanyInternalCommentDetailController);

  CompanyInternalCommentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CompanyInternalComment'];

  function CompanyInternalCommentDetailController($scope, $rootScope, $stateParams, previousState, entity, CompanyInternalComment) {
    var vm = this;

    vm.companyInternalComment = entity;
    vm.previousState = previousState.name;

    var unsubscribe = $rootScope.$on('recruitsmartApp:companyInternalCommentUpdate', function (event, result) {
      vm.companyInternalComment = result;
    });
    $scope.$on('$destroy', unsubscribe);
  }
})();

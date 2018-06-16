(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider
      .state('work-status', {
        parent: 'entity',
        url: '/work-status',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'WorkStatuses'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/work-status/work-statuses.html',
            controller: 'WorkStatusController',
            controllerAs: 'vm'
          }
        },
        resolve: {}
      })
      .state('work-status-detail', {
        parent: 'work-status',
        url: '/work-status/{id}',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'WorkStatus'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/work-status/work-status-detail.html',
            controller: 'WorkStatusDetailController',
            controllerAs: 'vm'
          }
        },
        resolve: {
          entity: ['$stateParams', 'WorkStatus', function ($stateParams, WorkStatus) {
            return WorkStatus.get({id: $stateParams.id}).$promise;
          }],
          previousState: ["$state", function ($state) {
            var currentStateData = {
              name: $state.current.name || 'work-status',
              params: $state.params,
              url: $state.href($state.current.name, $state.params)
            };
            return currentStateData;
          }]
        }
      })
      .state('work-status-detail.edit', {
        parent: 'work-status-detail',
        url: '/detail/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/work-status/work-status-dialog.html',
            controller: 'WorkStatusDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['WorkStatus', function (WorkStatus) {
                return WorkStatus.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('work-status.new', {
        parent: 'work-status',
        url: '/new',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/work-status/work-status-dialog.html',
            controller: 'WorkStatusDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: function () {
                return {
                  name: null,
                  description: null,
                  id: null
                };
              }
            }
          }).result.then(function () {
            $state.go('work-status', null, {reload: 'work-status'});
          }, function () {
            $state.go('work-status');
          });
        }]
      })
      .state('work-status.edit', {
        parent: 'work-status',
        url: '/{id}/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/work-status/work-status-dialog.html',
            controller: 'WorkStatusDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['WorkStatus', function (WorkStatus) {
                return WorkStatus.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('work-status', null, {reload: 'work-status'});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('work-status.delete', {
        parent: 'work-status',
        url: '/{id}/delete',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/work-status/work-status-delete-dialog.html',
            controller: 'WorkStatusDeleteController',
            controllerAs: 'vm',
            size: 'md',
            resolve: {
              entity: ['WorkStatus', function (WorkStatus) {
                return WorkStatus.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('work-status', null, {reload: 'work-status'});
          }, function () {
            $state.go('^');
          });
        }]
      });
  }

})();

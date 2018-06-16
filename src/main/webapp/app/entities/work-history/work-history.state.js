(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider
      .state('work-history', {
        parent: 'entity',
        url: '/work-history',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'WorkHistories'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/work-history/work-histories.html',
            controller: 'WorkHistoryController',
            controllerAs: 'vm'
          }
        },
        resolve: {}
      })
      .state('work-history-detail', {
        parent: 'work-history',
        url: '/work-history/{id}',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'WorkHistory'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/work-history/work-history-detail.html',
            controller: 'WorkHistoryDetailController',
            controllerAs: 'vm'
          }
        },
        resolve: {
          entity: ['$stateParams', 'WorkHistory', function ($stateParams, WorkHistory) {
            return WorkHistory.get({id: $stateParams.id}).$promise;
          }],
          previousState: ["$state", function ($state) {
            var currentStateData = {
              name: $state.current.name || 'work-history',
              params: $state.params,
              url: $state.href($state.current.name, $state.params)
            };
            return currentStateData;
          }]
        }
      })
      .state('work-history-detail.edit', {
        parent: 'work-history-detail',
        url: '/detail/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/work-history/work-history-dialog.html',
            controller: 'WorkHistoryDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['WorkHistory', function (WorkHistory) {
                return WorkHistory.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('work-history.new', {
        parent: 'work-history',
        url: '/new',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/work-history/work-history-dialog.html',
            controller: 'WorkHistoryDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: function () {
                return {
                  company: null,
                  title: null,
                  startDate: null,
                  endDate: null,
                  startingCompensation: null,
                  endingCompensation: null,
                  compensationType: null,
                  supervisor: null,
                  supervisorTitle: null,
                  supervisorPhone: null,
                  duties: null,
                  reasonForLeaving: null,
                  id: null
                };
              }
            }
          }).result.then(function () {
            $state.go('work-history', null, {reload: 'work-history'});
          }, function () {
            $state.go('work-history');
          });
        }]
      })
      .state('work-history.edit', {
        parent: 'work-history',
        url: '/{id}/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/work-history/work-history-dialog.html',
            controller: 'WorkHistoryDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['WorkHistory', function (WorkHistory) {
                return WorkHistory.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('work-history', null, {reload: 'work-history'});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('work-history.delete', {
        parent: 'work-history',
        url: '/{id}/delete',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/work-history/work-history-delete-dialog.html',
            controller: 'WorkHistoryDeleteController',
            controllerAs: 'vm',
            size: 'md',
            resolve: {
              entity: ['WorkHistory', function (WorkHistory) {
                return WorkHistory.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('work-history', null, {reload: 'work-history'});
          }, function () {
            $state.go('^');
          });
        }]
      });
  }

})();
